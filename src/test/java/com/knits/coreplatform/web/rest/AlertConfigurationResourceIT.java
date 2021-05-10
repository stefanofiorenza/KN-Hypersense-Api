package com.knits.coreplatform.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.knits.coreplatform.IntegrationTest;
import com.knits.coreplatform.domain.AlertConfiguration;
import com.knits.coreplatform.repository.AlertConfigurationRepository;
import com.knits.coreplatform.service.dto.AlertConfigurationDTO;
import com.knits.coreplatform.service.mapper.AlertConfigurationMapper;
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
 * Integration tests for the {@link AlertConfigurationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AlertConfigurationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CONFIGURATION = "AAAAAAAAAA";
    private static final String UPDATED_CONFIGURATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/alert-configurations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AlertConfigurationRepository alertConfigurationRepository;

    @Autowired
    private AlertConfigurationMapper alertConfigurationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlertConfigurationMockMvc;

    private AlertConfiguration alertConfiguration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlertConfiguration createEntity(EntityManager em) {
        AlertConfiguration alertConfiguration = new AlertConfiguration()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .configuration(DEFAULT_CONFIGURATION);
        return alertConfiguration;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlertConfiguration createUpdatedEntity(EntityManager em) {
        AlertConfiguration alertConfiguration = new AlertConfiguration()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .configuration(UPDATED_CONFIGURATION);
        return alertConfiguration;
    }

    @BeforeEach
    public void initTest() {
        alertConfiguration = createEntity(em);
    }

    @Test
    @Transactional
    void createAlertConfiguration() throws Exception {
        int databaseSizeBeforeCreate = alertConfigurationRepository.findAll().size();
        // Create the AlertConfiguration
        AlertConfigurationDTO alertConfigurationDTO = alertConfigurationMapper.toDto(alertConfiguration);
        restAlertConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alertConfigurationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AlertConfiguration in the database
        List<AlertConfiguration> alertConfigurationList = alertConfigurationRepository.findAll();
        assertThat(alertConfigurationList).hasSize(databaseSizeBeforeCreate + 1);
        AlertConfiguration testAlertConfiguration = alertConfigurationList.get(alertConfigurationList.size() - 1);
        assertThat(testAlertConfiguration.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAlertConfiguration.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAlertConfiguration.getConfiguration()).isEqualTo(DEFAULT_CONFIGURATION);
    }

    @Test
    @Transactional
    void createAlertConfigurationWithExistingId() throws Exception {
        // Create the AlertConfiguration with an existing ID
        alertConfiguration.setId(1L);
        AlertConfigurationDTO alertConfigurationDTO = alertConfigurationMapper.toDto(alertConfiguration);

        int databaseSizeBeforeCreate = alertConfigurationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlertConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alertConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlertConfiguration in the database
        List<AlertConfiguration> alertConfigurationList = alertConfigurationRepository.findAll();
        assertThat(alertConfigurationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAlertConfigurations() throws Exception {
        // Initialize the database
        alertConfigurationRepository.saveAndFlush(alertConfiguration);

        // Get all the alertConfigurationList
        restAlertConfigurationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alertConfiguration.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].configuration").value(hasItem(DEFAULT_CONFIGURATION)));
    }

    @Test
    @Transactional
    void getAlertConfiguration() throws Exception {
        // Initialize the database
        alertConfigurationRepository.saveAndFlush(alertConfiguration);

        // Get the alertConfiguration
        restAlertConfigurationMockMvc
            .perform(get(ENTITY_API_URL_ID, alertConfiguration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alertConfiguration.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.configuration").value(DEFAULT_CONFIGURATION));
    }

    @Test
    @Transactional
    void getNonExistingAlertConfiguration() throws Exception {
        // Get the alertConfiguration
        restAlertConfigurationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAlertConfiguration() throws Exception {
        // Initialize the database
        alertConfigurationRepository.saveAndFlush(alertConfiguration);

        int databaseSizeBeforeUpdate = alertConfigurationRepository.findAll().size();

        // Update the alertConfiguration
        AlertConfiguration updatedAlertConfiguration = alertConfigurationRepository.findById(alertConfiguration.getId()).get();
        // Disconnect from session so that the updates on updatedAlertConfiguration are not directly saved in db
        em.detach(updatedAlertConfiguration);
        updatedAlertConfiguration.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).configuration(UPDATED_CONFIGURATION);
        AlertConfigurationDTO alertConfigurationDTO = alertConfigurationMapper.toDto(updatedAlertConfiguration);

        restAlertConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, alertConfigurationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alertConfigurationDTO))
            )
            .andExpect(status().isOk());

        // Validate the AlertConfiguration in the database
        List<AlertConfiguration> alertConfigurationList = alertConfigurationRepository.findAll();
        assertThat(alertConfigurationList).hasSize(databaseSizeBeforeUpdate);
        AlertConfiguration testAlertConfiguration = alertConfigurationList.get(alertConfigurationList.size() - 1);
        assertThat(testAlertConfiguration.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAlertConfiguration.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAlertConfiguration.getConfiguration()).isEqualTo(UPDATED_CONFIGURATION);
    }

    @Test
    @Transactional
    void putNonExistingAlertConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = alertConfigurationRepository.findAll().size();
        alertConfiguration.setId(count.incrementAndGet());

        // Create the AlertConfiguration
        AlertConfigurationDTO alertConfigurationDTO = alertConfigurationMapper.toDto(alertConfiguration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlertConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, alertConfigurationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alertConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlertConfiguration in the database
        List<AlertConfiguration> alertConfigurationList = alertConfigurationRepository.findAll();
        assertThat(alertConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAlertConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = alertConfigurationRepository.findAll().size();
        alertConfiguration.setId(count.incrementAndGet());

        // Create the AlertConfiguration
        AlertConfigurationDTO alertConfigurationDTO = alertConfigurationMapper.toDto(alertConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlertConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alertConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlertConfiguration in the database
        List<AlertConfiguration> alertConfigurationList = alertConfigurationRepository.findAll();
        assertThat(alertConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAlertConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = alertConfigurationRepository.findAll().size();
        alertConfiguration.setId(count.incrementAndGet());

        // Create the AlertConfiguration
        AlertConfigurationDTO alertConfigurationDTO = alertConfigurationMapper.toDto(alertConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlertConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alertConfigurationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AlertConfiguration in the database
        List<AlertConfiguration> alertConfigurationList = alertConfigurationRepository.findAll();
        assertThat(alertConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAlertConfigurationWithPatch() throws Exception {
        // Initialize the database
        alertConfigurationRepository.saveAndFlush(alertConfiguration);

        int databaseSizeBeforeUpdate = alertConfigurationRepository.findAll().size();

        // Update the alertConfiguration using partial update
        AlertConfiguration partialUpdatedAlertConfiguration = new AlertConfiguration();
        partialUpdatedAlertConfiguration.setId(alertConfiguration.getId());

        partialUpdatedAlertConfiguration.description(UPDATED_DESCRIPTION);

        restAlertConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlertConfiguration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAlertConfiguration))
            )
            .andExpect(status().isOk());

        // Validate the AlertConfiguration in the database
        List<AlertConfiguration> alertConfigurationList = alertConfigurationRepository.findAll();
        assertThat(alertConfigurationList).hasSize(databaseSizeBeforeUpdate);
        AlertConfiguration testAlertConfiguration = alertConfigurationList.get(alertConfigurationList.size() - 1);
        assertThat(testAlertConfiguration.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAlertConfiguration.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAlertConfiguration.getConfiguration()).isEqualTo(DEFAULT_CONFIGURATION);
    }

    @Test
    @Transactional
    void fullUpdateAlertConfigurationWithPatch() throws Exception {
        // Initialize the database
        alertConfigurationRepository.saveAndFlush(alertConfiguration);

        int databaseSizeBeforeUpdate = alertConfigurationRepository.findAll().size();

        // Update the alertConfiguration using partial update
        AlertConfiguration partialUpdatedAlertConfiguration = new AlertConfiguration();
        partialUpdatedAlertConfiguration.setId(alertConfiguration.getId());

        partialUpdatedAlertConfiguration.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).configuration(UPDATED_CONFIGURATION);

        restAlertConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlertConfiguration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAlertConfiguration))
            )
            .andExpect(status().isOk());

        // Validate the AlertConfiguration in the database
        List<AlertConfiguration> alertConfigurationList = alertConfigurationRepository.findAll();
        assertThat(alertConfigurationList).hasSize(databaseSizeBeforeUpdate);
        AlertConfiguration testAlertConfiguration = alertConfigurationList.get(alertConfigurationList.size() - 1);
        assertThat(testAlertConfiguration.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAlertConfiguration.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAlertConfiguration.getConfiguration()).isEqualTo(UPDATED_CONFIGURATION);
    }

    @Test
    @Transactional
    void patchNonExistingAlertConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = alertConfigurationRepository.findAll().size();
        alertConfiguration.setId(count.incrementAndGet());

        // Create the AlertConfiguration
        AlertConfigurationDTO alertConfigurationDTO = alertConfigurationMapper.toDto(alertConfiguration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlertConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, alertConfigurationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alertConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlertConfiguration in the database
        List<AlertConfiguration> alertConfigurationList = alertConfigurationRepository.findAll();
        assertThat(alertConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAlertConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = alertConfigurationRepository.findAll().size();
        alertConfiguration.setId(count.incrementAndGet());

        // Create the AlertConfiguration
        AlertConfigurationDTO alertConfigurationDTO = alertConfigurationMapper.toDto(alertConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlertConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alertConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlertConfiguration in the database
        List<AlertConfiguration> alertConfigurationList = alertConfigurationRepository.findAll();
        assertThat(alertConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAlertConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = alertConfigurationRepository.findAll().size();
        alertConfiguration.setId(count.incrementAndGet());

        // Create the AlertConfiguration
        AlertConfigurationDTO alertConfigurationDTO = alertConfigurationMapper.toDto(alertConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlertConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alertConfigurationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AlertConfiguration in the database
        List<AlertConfiguration> alertConfigurationList = alertConfigurationRepository.findAll();
        assertThat(alertConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAlertConfiguration() throws Exception {
        // Initialize the database
        alertConfigurationRepository.saveAndFlush(alertConfiguration);

        int databaseSizeBeforeDelete = alertConfigurationRepository.findAll().size();

        // Delete the alertConfiguration
        restAlertConfigurationMockMvc
            .perform(delete(ENTITY_API_URL_ID, alertConfiguration.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AlertConfiguration> alertConfigurationList = alertConfigurationRepository.findAll();
        assertThat(alertConfigurationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
