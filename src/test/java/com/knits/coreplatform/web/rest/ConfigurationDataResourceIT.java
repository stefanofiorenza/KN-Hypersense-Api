package com.knits.coreplatform.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.knits.coreplatform.IntegrationTest;
import com.knits.coreplatform.domain.ConfigurationData;
import com.knits.coreplatform.repository.ConfigurationDataRepository;
import com.knits.coreplatform.service.dto.ConfigurationDataDTO;
import com.knits.coreplatform.service.mapper.ConfigurationDataMapper;
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
 * Integration tests for the {@link ConfigurationDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConfigurationDataResourceIT {

    private static final String ENTITY_API_URL = "/api/configuration-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConfigurationDataRepository configurationDataRepository;

    @Autowired
    private ConfigurationDataMapper configurationDataMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConfigurationDataMockMvc;

    private ConfigurationData configurationData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConfigurationData createEntity(EntityManager em) {
        ConfigurationData configurationData = new ConfigurationData();
        return configurationData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConfigurationData createUpdatedEntity(EntityManager em) {
        ConfigurationData configurationData = new ConfigurationData();
        return configurationData;
    }

    @BeforeEach
    public void initTest() {
        configurationData = createEntity(em);
    }

    @Test
    @Transactional
    void createConfigurationData() throws Exception {
        int databaseSizeBeforeCreate = configurationDataRepository.findAll().size();
        // Create the ConfigurationData
        ConfigurationDataDTO configurationDataDTO = configurationDataMapper.toDto(configurationData);
        restConfigurationDataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configurationDataDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ConfigurationData in the database
        List<ConfigurationData> configurationDataList = configurationDataRepository.findAll();
        assertThat(configurationDataList).hasSize(databaseSizeBeforeCreate + 1);
        ConfigurationData testConfigurationData = configurationDataList.get(configurationDataList.size() - 1);
    }

    @Test
    @Transactional
    void createConfigurationDataWithExistingId() throws Exception {
        // Create the ConfigurationData with an existing ID
        configurationData.setId(1L);
        ConfigurationDataDTO configurationDataDTO = configurationDataMapper.toDto(configurationData);

        int databaseSizeBeforeCreate = configurationDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConfigurationDataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configurationDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConfigurationData in the database
        List<ConfigurationData> configurationDataList = configurationDataRepository.findAll();
        assertThat(configurationDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllConfigurationData() throws Exception {
        // Initialize the database
        configurationDataRepository.saveAndFlush(configurationData);

        // Get all the configurationDataList
        restConfigurationDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(configurationData.getId().intValue())));
    }

    @Test
    @Transactional
    void getConfigurationData() throws Exception {
        // Initialize the database
        configurationDataRepository.saveAndFlush(configurationData);

        // Get the configurationData
        restConfigurationDataMockMvc
            .perform(get(ENTITY_API_URL_ID, configurationData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(configurationData.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingConfigurationData() throws Exception {
        // Get the configurationData
        restConfigurationDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewConfigurationData() throws Exception {
        // Initialize the database
        configurationDataRepository.saveAndFlush(configurationData);

        int databaseSizeBeforeUpdate = configurationDataRepository.findAll().size();

        // Update the configurationData
        ConfigurationData updatedConfigurationData = configurationDataRepository.findById(configurationData.getId()).get();
        // Disconnect from session so that the updates on updatedConfigurationData are not directly saved in db
        em.detach(updatedConfigurationData);
        ConfigurationDataDTO configurationDataDTO = configurationDataMapper.toDto(updatedConfigurationData);

        restConfigurationDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, configurationDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configurationDataDTO))
            )
            .andExpect(status().isOk());

        // Validate the ConfigurationData in the database
        List<ConfigurationData> configurationDataList = configurationDataRepository.findAll();
        assertThat(configurationDataList).hasSize(databaseSizeBeforeUpdate);
        ConfigurationData testConfigurationData = configurationDataList.get(configurationDataList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingConfigurationData() throws Exception {
        int databaseSizeBeforeUpdate = configurationDataRepository.findAll().size();
        configurationData.setId(count.incrementAndGet());

        // Create the ConfigurationData
        ConfigurationDataDTO configurationDataDTO = configurationDataMapper.toDto(configurationData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfigurationDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, configurationDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configurationDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConfigurationData in the database
        List<ConfigurationData> configurationDataList = configurationDataRepository.findAll();
        assertThat(configurationDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConfigurationData() throws Exception {
        int databaseSizeBeforeUpdate = configurationDataRepository.findAll().size();
        configurationData.setId(count.incrementAndGet());

        // Create the ConfigurationData
        ConfigurationDataDTO configurationDataDTO = configurationDataMapper.toDto(configurationData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigurationDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configurationDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConfigurationData in the database
        List<ConfigurationData> configurationDataList = configurationDataRepository.findAll();
        assertThat(configurationDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConfigurationData() throws Exception {
        int databaseSizeBeforeUpdate = configurationDataRepository.findAll().size();
        configurationData.setId(count.incrementAndGet());

        // Create the ConfigurationData
        ConfigurationDataDTO configurationDataDTO = configurationDataMapper.toDto(configurationData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigurationDataMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(configurationDataDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConfigurationData in the database
        List<ConfigurationData> configurationDataList = configurationDataRepository.findAll();
        assertThat(configurationDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConfigurationDataWithPatch() throws Exception {
        // Initialize the database
        configurationDataRepository.saveAndFlush(configurationData);

        int databaseSizeBeforeUpdate = configurationDataRepository.findAll().size();

        // Update the configurationData using partial update
        ConfigurationData partialUpdatedConfigurationData = new ConfigurationData();
        partialUpdatedConfigurationData.setId(configurationData.getId());

        restConfigurationDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConfigurationData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConfigurationData))
            )
            .andExpect(status().isOk());

        // Validate the ConfigurationData in the database
        List<ConfigurationData> configurationDataList = configurationDataRepository.findAll();
        assertThat(configurationDataList).hasSize(databaseSizeBeforeUpdate);
        ConfigurationData testConfigurationData = configurationDataList.get(configurationDataList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateConfigurationDataWithPatch() throws Exception {
        // Initialize the database
        configurationDataRepository.saveAndFlush(configurationData);

        int databaseSizeBeforeUpdate = configurationDataRepository.findAll().size();

        // Update the configurationData using partial update
        ConfigurationData partialUpdatedConfigurationData = new ConfigurationData();
        partialUpdatedConfigurationData.setId(configurationData.getId());

        restConfigurationDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConfigurationData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConfigurationData))
            )
            .andExpect(status().isOk());

        // Validate the ConfigurationData in the database
        List<ConfigurationData> configurationDataList = configurationDataRepository.findAll();
        assertThat(configurationDataList).hasSize(databaseSizeBeforeUpdate);
        ConfigurationData testConfigurationData = configurationDataList.get(configurationDataList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingConfigurationData() throws Exception {
        int databaseSizeBeforeUpdate = configurationDataRepository.findAll().size();
        configurationData.setId(count.incrementAndGet());

        // Create the ConfigurationData
        ConfigurationDataDTO configurationDataDTO = configurationDataMapper.toDto(configurationData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfigurationDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, configurationDataDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(configurationDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConfigurationData in the database
        List<ConfigurationData> configurationDataList = configurationDataRepository.findAll();
        assertThat(configurationDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConfigurationData() throws Exception {
        int databaseSizeBeforeUpdate = configurationDataRepository.findAll().size();
        configurationData.setId(count.incrementAndGet());

        // Create the ConfigurationData
        ConfigurationDataDTO configurationDataDTO = configurationDataMapper.toDto(configurationData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigurationDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(configurationDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConfigurationData in the database
        List<ConfigurationData> configurationDataList = configurationDataRepository.findAll();
        assertThat(configurationDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConfigurationData() throws Exception {
        int databaseSizeBeforeUpdate = configurationDataRepository.findAll().size();
        configurationData.setId(count.incrementAndGet());

        // Create the ConfigurationData
        ConfigurationDataDTO configurationDataDTO = configurationDataMapper.toDto(configurationData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigurationDataMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(configurationDataDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConfigurationData in the database
        List<ConfigurationData> configurationDataList = configurationDataRepository.findAll();
        assertThat(configurationDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConfigurationData() throws Exception {
        // Initialize the database
        configurationDataRepository.saveAndFlush(configurationData);

        int databaseSizeBeforeDelete = configurationDataRepository.findAll().size();

        // Delete the configurationData
        restConfigurationDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, configurationData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConfigurationData> configurationDataList = configurationDataRepository.findAll();
        assertThat(configurationDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
