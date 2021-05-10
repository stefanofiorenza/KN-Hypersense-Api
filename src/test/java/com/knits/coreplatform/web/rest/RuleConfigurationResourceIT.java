package com.knits.coreplatform.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.knits.coreplatform.IntegrationTest;
import com.knits.coreplatform.domain.RuleConfiguration;
import com.knits.coreplatform.repository.RuleConfigurationRepository;
import com.knits.coreplatform.service.dto.RuleConfigurationDTO;
import com.knits.coreplatform.service.mapper.RuleConfigurationMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RuleConfigurationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RuleConfigurationResourceIT {

    private static final String ENTITY_API_URL = "/api/rule-configurations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RuleConfigurationRepository ruleConfigurationRepository;

    @Autowired
    private RuleConfigurationMapper ruleConfigurationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRuleConfigurationMockMvc;

    private RuleConfiguration ruleConfiguration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RuleConfiguration createEntity(EntityManager em) {
        RuleConfiguration ruleConfiguration = new RuleConfiguration();
        return ruleConfiguration;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RuleConfiguration createUpdatedEntity(EntityManager em) {
        RuleConfiguration ruleConfiguration = new RuleConfiguration();
        return ruleConfiguration;
    }

    @BeforeEach
    public void initTest() {
        ruleConfiguration = createEntity(em);
    }

    @Test
    @Transactional
    void createRuleConfiguration() throws Exception {
        int databaseSizeBeforeCreate = ruleConfigurationRepository.findAll().size();
        // Create the RuleConfiguration
        RuleConfigurationDTO ruleConfigurationDTO = ruleConfigurationMapper.toDto(ruleConfiguration);
        restRuleConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ruleConfigurationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RuleConfiguration in the database
        List<RuleConfiguration> ruleConfigurationList = ruleConfigurationRepository.findAll();
        assertThat(ruleConfigurationList).hasSize(databaseSizeBeforeCreate + 1);
        RuleConfiguration testRuleConfiguration = ruleConfigurationList.get(ruleConfigurationList.size() - 1);
    }

    @Test
    @Transactional
    void createRuleConfigurationWithExistingId() throws Exception {
        // Create the RuleConfiguration with an existing ID
        ruleConfiguration.setId(1L);
        RuleConfigurationDTO ruleConfigurationDTO = ruleConfigurationMapper.toDto(ruleConfiguration);

        int databaseSizeBeforeCreate = ruleConfigurationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRuleConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ruleConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RuleConfiguration in the database
        List<RuleConfiguration> ruleConfigurationList = ruleConfigurationRepository.findAll();
        assertThat(ruleConfigurationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRuleConfigurations() throws Exception {
        // Initialize the database
        ruleConfigurationRepository.saveAndFlush(ruleConfiguration);

        // Get all the ruleConfigurationList
        restRuleConfigurationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ruleConfiguration.getId().intValue())));
    }

    @Test
    @Transactional
    void getRuleConfiguration() throws Exception {
        // Initialize the database
        ruleConfigurationRepository.saveAndFlush(ruleConfiguration);

        // Get the ruleConfiguration
        restRuleConfigurationMockMvc
            .perform(get(ENTITY_API_URL_ID, ruleConfiguration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ruleConfiguration.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingRuleConfiguration() throws Exception {
        // Get the ruleConfiguration
        restRuleConfigurationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRuleConfiguration() throws Exception {
        // Initialize the database
        ruleConfigurationRepository.saveAndFlush(ruleConfiguration);

        int databaseSizeBeforeUpdate = ruleConfigurationRepository.findAll().size();

        // Update the ruleConfiguration
        RuleConfiguration updatedRuleConfiguration = ruleConfigurationRepository.findById(ruleConfiguration.getId()).get();
        // Disconnect from session so that the updates on updatedRuleConfiguration are not directly saved in db
        em.detach(updatedRuleConfiguration);
        RuleConfigurationDTO ruleConfigurationDTO = ruleConfigurationMapper.toDto(updatedRuleConfiguration);

        restRuleConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ruleConfigurationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ruleConfigurationDTO))
            )
            .andExpect(status().isOk());

        // Validate the RuleConfiguration in the database
        List<RuleConfiguration> ruleConfigurationList = ruleConfigurationRepository.findAll();
        assertThat(ruleConfigurationList).hasSize(databaseSizeBeforeUpdate);
        RuleConfiguration testRuleConfiguration = ruleConfigurationList.get(ruleConfigurationList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingRuleConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = ruleConfigurationRepository.findAll().size();
        ruleConfiguration.setId(count.incrementAndGet());

        // Create the RuleConfiguration
        RuleConfigurationDTO ruleConfigurationDTO = ruleConfigurationMapper.toDto(ruleConfiguration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRuleConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ruleConfigurationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ruleConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RuleConfiguration in the database
        List<RuleConfiguration> ruleConfigurationList = ruleConfigurationRepository.findAll();
        assertThat(ruleConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRuleConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = ruleConfigurationRepository.findAll().size();
        ruleConfiguration.setId(count.incrementAndGet());

        // Create the RuleConfiguration
        RuleConfigurationDTO ruleConfigurationDTO = ruleConfigurationMapper.toDto(ruleConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRuleConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ruleConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RuleConfiguration in the database
        List<RuleConfiguration> ruleConfigurationList = ruleConfigurationRepository.findAll();
        assertThat(ruleConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRuleConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = ruleConfigurationRepository.findAll().size();
        ruleConfiguration.setId(count.incrementAndGet());

        // Create the RuleConfiguration
        RuleConfigurationDTO ruleConfigurationDTO = ruleConfigurationMapper.toDto(ruleConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRuleConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ruleConfigurationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RuleConfiguration in the database
        List<RuleConfiguration> ruleConfigurationList = ruleConfigurationRepository.findAll();
        assertThat(ruleConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRuleConfigurationWithPatch() throws Exception {
        // Initialize the database
        ruleConfigurationRepository.saveAndFlush(ruleConfiguration);

        int databaseSizeBeforeUpdate = ruleConfigurationRepository.findAll().size();

        // Update the ruleConfiguration using partial update
        RuleConfiguration partialUpdatedRuleConfiguration = new RuleConfiguration();
        partialUpdatedRuleConfiguration.setId(ruleConfiguration.getId());

        restRuleConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRuleConfiguration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRuleConfiguration))
            )
            .andExpect(status().isOk());

        // Validate the RuleConfiguration in the database
        List<RuleConfiguration> ruleConfigurationList = ruleConfigurationRepository.findAll();
        assertThat(ruleConfigurationList).hasSize(databaseSizeBeforeUpdate);
        RuleConfiguration testRuleConfiguration = ruleConfigurationList.get(ruleConfigurationList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateRuleConfigurationWithPatch() throws Exception {
        // Initialize the database
        ruleConfigurationRepository.saveAndFlush(ruleConfiguration);

        int databaseSizeBeforeUpdate = ruleConfigurationRepository.findAll().size();

        // Update the ruleConfiguration using partial update
        RuleConfiguration partialUpdatedRuleConfiguration = new RuleConfiguration();
        partialUpdatedRuleConfiguration.setId(ruleConfiguration.getId());

        restRuleConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRuleConfiguration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRuleConfiguration))
            )
            .andExpect(status().isOk());

        // Validate the RuleConfiguration in the database
        List<RuleConfiguration> ruleConfigurationList = ruleConfigurationRepository.findAll();
        assertThat(ruleConfigurationList).hasSize(databaseSizeBeforeUpdate);
        RuleConfiguration testRuleConfiguration = ruleConfigurationList.get(ruleConfigurationList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingRuleConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = ruleConfigurationRepository.findAll().size();
        ruleConfiguration.setId(count.incrementAndGet());

        // Create the RuleConfiguration
        RuleConfigurationDTO ruleConfigurationDTO = ruleConfigurationMapper.toDto(ruleConfiguration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRuleConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ruleConfigurationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ruleConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RuleConfiguration in the database
        List<RuleConfiguration> ruleConfigurationList = ruleConfigurationRepository.findAll();
        assertThat(ruleConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRuleConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = ruleConfigurationRepository.findAll().size();
        ruleConfiguration.setId(count.incrementAndGet());

        // Create the RuleConfiguration
        RuleConfigurationDTO ruleConfigurationDTO = ruleConfigurationMapper.toDto(ruleConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRuleConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ruleConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RuleConfiguration in the database
        List<RuleConfiguration> ruleConfigurationList = ruleConfigurationRepository.findAll();
        assertThat(ruleConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRuleConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = ruleConfigurationRepository.findAll().size();
        ruleConfiguration.setId(count.incrementAndGet());

        // Create the RuleConfiguration
        RuleConfigurationDTO ruleConfigurationDTO = ruleConfigurationMapper.toDto(ruleConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRuleConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ruleConfigurationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RuleConfiguration in the database
        List<RuleConfiguration> ruleConfigurationList = ruleConfigurationRepository.findAll();
        assertThat(ruleConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRuleConfiguration() throws Exception {
        // Initialize the database
        ruleConfigurationRepository.saveAndFlush(ruleConfiguration);

        int databaseSizeBeforeDelete = ruleConfigurationRepository.findAll().size();

        // Delete the ruleConfiguration
        restRuleConfigurationMockMvc
            .perform(delete(ENTITY_API_URL_ID, ruleConfiguration.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RuleConfiguration> ruleConfigurationList = ruleConfigurationRepository.findAll();
        assertThat(ruleConfigurationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
