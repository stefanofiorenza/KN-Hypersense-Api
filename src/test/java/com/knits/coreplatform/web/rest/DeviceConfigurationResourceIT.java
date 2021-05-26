package com.knits.coreplatform.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.knits.coreplatform.IntegrationTest;
import com.knits.coreplatform.domain.DeviceConfiguration;
import com.knits.coreplatform.repository.DeviceConfigurationRepository;
import com.knits.coreplatform.service.dto.DeviceConfigurationDTO;
import com.knits.coreplatform.service.mapper.DeviceConfigurationMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link DeviceConfigurationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DeviceConfigurationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_U_UID = "AAAAAAAAAA";
    private static final String UPDATED_U_UID = "BBBBBBBBBB";

    private static final byte[] DEFAULT_TOKEN = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_TOKEN = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_TOKEN_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_TOKEN_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/device-configurations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DeviceConfigurationRepository deviceConfigurationRepository;

    @Autowired
    private DeviceConfigurationMapper deviceConfigurationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeviceConfigurationMockMvc;

    private DeviceConfiguration deviceConfiguration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceConfiguration createEntity(EntityManager em) {
        DeviceConfiguration deviceConfiguration = new DeviceConfiguration()
            .name(DEFAULT_NAME)
            .uUID(DEFAULT_U_UID)
            .token(DEFAULT_TOKEN)
            .tokenContentType(DEFAULT_TOKEN_CONTENT_TYPE);
        return deviceConfiguration;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceConfiguration createUpdatedEntity(EntityManager em) {
        DeviceConfiguration deviceConfiguration = new DeviceConfiguration()
            .name(UPDATED_NAME)
            .uUID(UPDATED_U_UID)
            .token(UPDATED_TOKEN)
            .tokenContentType(UPDATED_TOKEN_CONTENT_TYPE);
        return deviceConfiguration;
    }

    @BeforeEach
    public void initTest() {
        deviceConfiguration = createEntity(em);
    }

    @Test
    @Transactional
    void createDeviceConfiguration() throws Exception {
        int databaseSizeBeforeCreate = deviceConfigurationRepository.findAll().size();
        // Create the DeviceConfiguration
        DeviceConfigurationDTO deviceConfigurationDTO = deviceConfigurationMapper.toDto(deviceConfiguration);
        restDeviceConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deviceConfigurationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DeviceConfiguration in the database
        List<DeviceConfiguration> deviceConfigurationList = deviceConfigurationRepository.findAll();
        assertThat(deviceConfigurationList).hasSize(databaseSizeBeforeCreate + 1);
        DeviceConfiguration testDeviceConfiguration = deviceConfigurationList.get(deviceConfigurationList.size() - 1);
        assertThat(testDeviceConfiguration.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDeviceConfiguration.getuUID()).isEqualTo(DEFAULT_U_UID);
        assertThat(testDeviceConfiguration.getToken()).isEqualTo(DEFAULT_TOKEN);
        assertThat(testDeviceConfiguration.getTokenContentType()).isEqualTo(DEFAULT_TOKEN_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createDeviceConfigurationWithExistingId() throws Exception {
        // Create the DeviceConfiguration with an existing ID
        deviceConfiguration.setId(1L);
        DeviceConfigurationDTO deviceConfigurationDTO = deviceConfigurationMapper.toDto(deviceConfiguration);

        int databaseSizeBeforeCreate = deviceConfigurationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviceConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deviceConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeviceConfiguration in the database
        List<DeviceConfiguration> deviceConfigurationList = deviceConfigurationRepository.findAll();
        assertThat(deviceConfigurationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDeviceConfigurations() throws Exception {
        // Initialize the database
        deviceConfigurationRepository.saveAndFlush(deviceConfiguration);

        // Get all the deviceConfigurationList
        restDeviceConfigurationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deviceConfiguration.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].uUID").value(hasItem(DEFAULT_U_UID)))
            .andExpect(jsonPath("$.[*].tokenContentType").value(hasItem(DEFAULT_TOKEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].token").value(hasItem(Base64Utils.encodeToString(DEFAULT_TOKEN))));
    }

    @Test
    @Transactional
    void getDeviceConfiguration() throws Exception {
        // Initialize the database
        deviceConfigurationRepository.saveAndFlush(deviceConfiguration);

        // Get the deviceConfiguration
        restDeviceConfigurationMockMvc
            .perform(get(ENTITY_API_URL_ID, deviceConfiguration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deviceConfiguration.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.uUID").value(DEFAULT_U_UID))
            .andExpect(jsonPath("$.tokenContentType").value(DEFAULT_TOKEN_CONTENT_TYPE))
            .andExpect(jsonPath("$.token").value(Base64Utils.encodeToString(DEFAULT_TOKEN)));
    }

    @Test
    @Transactional
    void getNonExistingDeviceConfiguration() throws Exception {
        // Get the deviceConfiguration
        restDeviceConfigurationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDeviceConfiguration() throws Exception {
        // Initialize the database
        deviceConfigurationRepository.saveAndFlush(deviceConfiguration);

        int databaseSizeBeforeUpdate = deviceConfigurationRepository.findAll().size();

        // Update the deviceConfiguration
        DeviceConfiguration updatedDeviceConfiguration = deviceConfigurationRepository.findById(deviceConfiguration.getId()).get();
        // Disconnect from session so that the updates on updatedDeviceConfiguration are not directly saved in db
        em.detach(updatedDeviceConfiguration);
        updatedDeviceConfiguration.name(UPDATED_NAME).uUID(UPDATED_U_UID).token(UPDATED_TOKEN).tokenContentType(UPDATED_TOKEN_CONTENT_TYPE);
        DeviceConfigurationDTO deviceConfigurationDTO = deviceConfigurationMapper.toDto(updatedDeviceConfiguration);

        restDeviceConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deviceConfigurationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deviceConfigurationDTO))
            )
            .andExpect(status().isOk());

        // Validate the DeviceConfiguration in the database
        List<DeviceConfiguration> deviceConfigurationList = deviceConfigurationRepository.findAll();
        assertThat(deviceConfigurationList).hasSize(databaseSizeBeforeUpdate);
        DeviceConfiguration testDeviceConfiguration = deviceConfigurationList.get(deviceConfigurationList.size() - 1);
        assertThat(testDeviceConfiguration.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDeviceConfiguration.getuUID()).isEqualTo(UPDATED_U_UID);
        assertThat(testDeviceConfiguration.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testDeviceConfiguration.getTokenContentType()).isEqualTo(UPDATED_TOKEN_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingDeviceConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = deviceConfigurationRepository.findAll().size();
        deviceConfiguration.setId(count.incrementAndGet());

        // Create the DeviceConfiguration
        DeviceConfigurationDTO deviceConfigurationDTO = deviceConfigurationMapper.toDto(deviceConfiguration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviceConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deviceConfigurationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deviceConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeviceConfiguration in the database
        List<DeviceConfiguration> deviceConfigurationList = deviceConfigurationRepository.findAll();
        assertThat(deviceConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDeviceConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = deviceConfigurationRepository.findAll().size();
        deviceConfiguration.setId(count.incrementAndGet());

        // Create the DeviceConfiguration
        DeviceConfigurationDTO deviceConfigurationDTO = deviceConfigurationMapper.toDto(deviceConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviceConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deviceConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeviceConfiguration in the database
        List<DeviceConfiguration> deviceConfigurationList = deviceConfigurationRepository.findAll();
        assertThat(deviceConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDeviceConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = deviceConfigurationRepository.findAll().size();
        deviceConfiguration.setId(count.incrementAndGet());

        // Create the DeviceConfiguration
        DeviceConfigurationDTO deviceConfigurationDTO = deviceConfigurationMapper.toDto(deviceConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviceConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deviceConfigurationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DeviceConfiguration in the database
        List<DeviceConfiguration> deviceConfigurationList = deviceConfigurationRepository.findAll();
        assertThat(deviceConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDeviceConfigurationWithPatch() throws Exception {
        // Initialize the database
        deviceConfigurationRepository.saveAndFlush(deviceConfiguration);

        int databaseSizeBeforeUpdate = deviceConfigurationRepository.findAll().size();

        // Update the deviceConfiguration using partial update
        DeviceConfiguration partialUpdatedDeviceConfiguration = new DeviceConfiguration();
        partialUpdatedDeviceConfiguration.setId(deviceConfiguration.getId());

        partialUpdatedDeviceConfiguration
            .name(UPDATED_NAME)
            .uUID(UPDATED_U_UID)
            .token(UPDATED_TOKEN)
            .tokenContentType(UPDATED_TOKEN_CONTENT_TYPE);

        restDeviceConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeviceConfiguration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeviceConfiguration))
            )
            .andExpect(status().isOk());

        // Validate the DeviceConfiguration in the database
        List<DeviceConfiguration> deviceConfigurationList = deviceConfigurationRepository.findAll();
        assertThat(deviceConfigurationList).hasSize(databaseSizeBeforeUpdate);
        DeviceConfiguration testDeviceConfiguration = deviceConfigurationList.get(deviceConfigurationList.size() - 1);
        assertThat(testDeviceConfiguration.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDeviceConfiguration.getuUID()).isEqualTo(UPDATED_U_UID);
        assertThat(testDeviceConfiguration.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testDeviceConfiguration.getTokenContentType()).isEqualTo(UPDATED_TOKEN_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateDeviceConfigurationWithPatch() throws Exception {
        // Initialize the database
        deviceConfigurationRepository.saveAndFlush(deviceConfiguration);

        int databaseSizeBeforeUpdate = deviceConfigurationRepository.findAll().size();

        // Update the deviceConfiguration using partial update
        DeviceConfiguration partialUpdatedDeviceConfiguration = new DeviceConfiguration();
        partialUpdatedDeviceConfiguration.setId(deviceConfiguration.getId());

        partialUpdatedDeviceConfiguration
            .name(UPDATED_NAME)
            .uUID(UPDATED_U_UID)
            .token(UPDATED_TOKEN)
            .tokenContentType(UPDATED_TOKEN_CONTENT_TYPE);

        restDeviceConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeviceConfiguration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeviceConfiguration))
            )
            .andExpect(status().isOk());

        // Validate the DeviceConfiguration in the database
        List<DeviceConfiguration> deviceConfigurationList = deviceConfigurationRepository.findAll();
        assertThat(deviceConfigurationList).hasSize(databaseSizeBeforeUpdate);
        DeviceConfiguration testDeviceConfiguration = deviceConfigurationList.get(deviceConfigurationList.size() - 1);
        assertThat(testDeviceConfiguration.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDeviceConfiguration.getuUID()).isEqualTo(UPDATED_U_UID);
        assertThat(testDeviceConfiguration.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testDeviceConfiguration.getTokenContentType()).isEqualTo(UPDATED_TOKEN_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingDeviceConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = deviceConfigurationRepository.findAll().size();
        deviceConfiguration.setId(count.incrementAndGet());

        // Create the DeviceConfiguration
        DeviceConfigurationDTO deviceConfigurationDTO = deviceConfigurationMapper.toDto(deviceConfiguration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviceConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, deviceConfigurationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deviceConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeviceConfiguration in the database
        List<DeviceConfiguration> deviceConfigurationList = deviceConfigurationRepository.findAll();
        assertThat(deviceConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDeviceConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = deviceConfigurationRepository.findAll().size();
        deviceConfiguration.setId(count.incrementAndGet());

        // Create the DeviceConfiguration
        DeviceConfigurationDTO deviceConfigurationDTO = deviceConfigurationMapper.toDto(deviceConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviceConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deviceConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeviceConfiguration in the database
        List<DeviceConfiguration> deviceConfigurationList = deviceConfigurationRepository.findAll();
        assertThat(deviceConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDeviceConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = deviceConfigurationRepository.findAll().size();
        deviceConfiguration.setId(count.incrementAndGet());

        // Create the DeviceConfiguration
        DeviceConfigurationDTO deviceConfigurationDTO = deviceConfigurationMapper.toDto(deviceConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviceConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deviceConfigurationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DeviceConfiguration in the database
        List<DeviceConfiguration> deviceConfigurationList = deviceConfigurationRepository.findAll();
        assertThat(deviceConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDeviceConfiguration() throws Exception {
        // Initialize the database
        deviceConfigurationRepository.saveAndFlush(deviceConfiguration);

        int databaseSizeBeforeDelete = deviceConfigurationRepository.findAll().size();

        // Delete the deviceConfiguration
        restDeviceConfigurationMockMvc
            .perform(delete(ENTITY_API_URL_ID, deviceConfiguration.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeviceConfiguration> deviceConfigurationList = deviceConfigurationRepository.findAll();
        assertThat(deviceConfigurationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
