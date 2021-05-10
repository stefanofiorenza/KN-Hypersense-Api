package com.knits.coreplatform.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.knits.coreplatform.IntegrationTest;
import com.knits.coreplatform.domain.DeviceGroup;
import com.knits.coreplatform.repository.DeviceGroupRepository;
import com.knits.coreplatform.service.dto.DeviceGroupDTO;
import com.knits.coreplatform.service.mapper.DeviceGroupMapper;
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
 * Integration tests for the {@link DeviceGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DeviceGroupResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/device-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DeviceGroupRepository deviceGroupRepository;

    @Autowired
    private DeviceGroupMapper deviceGroupMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeviceGroupMockMvc;

    private DeviceGroup deviceGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceGroup createEntity(EntityManager em) {
        DeviceGroup deviceGroup = new DeviceGroup().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return deviceGroup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceGroup createUpdatedEntity(EntityManager em) {
        DeviceGroup deviceGroup = new DeviceGroup().name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return deviceGroup;
    }

    @BeforeEach
    public void initTest() {
        deviceGroup = createEntity(em);
    }

    @Test
    @Transactional
    void createDeviceGroup() throws Exception {
        int databaseSizeBeforeCreate = deviceGroupRepository.findAll().size();
        // Create the DeviceGroup
        DeviceGroupDTO deviceGroupDTO = deviceGroupMapper.toDto(deviceGroup);
        restDeviceGroupMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deviceGroupDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DeviceGroup in the database
        List<DeviceGroup> deviceGroupList = deviceGroupRepository.findAll();
        assertThat(deviceGroupList).hasSize(databaseSizeBeforeCreate + 1);
        DeviceGroup testDeviceGroup = deviceGroupList.get(deviceGroupList.size() - 1);
        assertThat(testDeviceGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDeviceGroup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createDeviceGroupWithExistingId() throws Exception {
        // Create the DeviceGroup with an existing ID
        deviceGroup.setId(1L);
        DeviceGroupDTO deviceGroupDTO = deviceGroupMapper.toDto(deviceGroup);

        int databaseSizeBeforeCreate = deviceGroupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviceGroupMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deviceGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeviceGroup in the database
        List<DeviceGroup> deviceGroupList = deviceGroupRepository.findAll();
        assertThat(deviceGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDeviceGroups() throws Exception {
        // Initialize the database
        deviceGroupRepository.saveAndFlush(deviceGroup);

        // Get all the deviceGroupList
        restDeviceGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deviceGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getDeviceGroup() throws Exception {
        // Initialize the database
        deviceGroupRepository.saveAndFlush(deviceGroup);

        // Get the deviceGroup
        restDeviceGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, deviceGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deviceGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingDeviceGroup() throws Exception {
        // Get the deviceGroup
        restDeviceGroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDeviceGroup() throws Exception {
        // Initialize the database
        deviceGroupRepository.saveAndFlush(deviceGroup);

        int databaseSizeBeforeUpdate = deviceGroupRepository.findAll().size();

        // Update the deviceGroup
        DeviceGroup updatedDeviceGroup = deviceGroupRepository.findById(deviceGroup.getId()).get();
        // Disconnect from session so that the updates on updatedDeviceGroup are not directly saved in db
        em.detach(updatedDeviceGroup);
        updatedDeviceGroup.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        DeviceGroupDTO deviceGroupDTO = deviceGroupMapper.toDto(updatedDeviceGroup);

        restDeviceGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deviceGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deviceGroupDTO))
            )
            .andExpect(status().isOk());

        // Validate the DeviceGroup in the database
        List<DeviceGroup> deviceGroupList = deviceGroupRepository.findAll();
        assertThat(deviceGroupList).hasSize(databaseSizeBeforeUpdate);
        DeviceGroup testDeviceGroup = deviceGroupList.get(deviceGroupList.size() - 1);
        assertThat(testDeviceGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDeviceGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingDeviceGroup() throws Exception {
        int databaseSizeBeforeUpdate = deviceGroupRepository.findAll().size();
        deviceGroup.setId(count.incrementAndGet());

        // Create the DeviceGroup
        DeviceGroupDTO deviceGroupDTO = deviceGroupMapper.toDto(deviceGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviceGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deviceGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deviceGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeviceGroup in the database
        List<DeviceGroup> deviceGroupList = deviceGroupRepository.findAll();
        assertThat(deviceGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDeviceGroup() throws Exception {
        int databaseSizeBeforeUpdate = deviceGroupRepository.findAll().size();
        deviceGroup.setId(count.incrementAndGet());

        // Create the DeviceGroup
        DeviceGroupDTO deviceGroupDTO = deviceGroupMapper.toDto(deviceGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviceGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deviceGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeviceGroup in the database
        List<DeviceGroup> deviceGroupList = deviceGroupRepository.findAll();
        assertThat(deviceGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDeviceGroup() throws Exception {
        int databaseSizeBeforeUpdate = deviceGroupRepository.findAll().size();
        deviceGroup.setId(count.incrementAndGet());

        // Create the DeviceGroup
        DeviceGroupDTO deviceGroupDTO = deviceGroupMapper.toDto(deviceGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviceGroupMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deviceGroupDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DeviceGroup in the database
        List<DeviceGroup> deviceGroupList = deviceGroupRepository.findAll();
        assertThat(deviceGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDeviceGroupWithPatch() throws Exception {
        // Initialize the database
        deviceGroupRepository.saveAndFlush(deviceGroup);

        int databaseSizeBeforeUpdate = deviceGroupRepository.findAll().size();

        // Update the deviceGroup using partial update
        DeviceGroup partialUpdatedDeviceGroup = new DeviceGroup();
        partialUpdatedDeviceGroup.setId(deviceGroup.getId());

        partialUpdatedDeviceGroup.description(UPDATED_DESCRIPTION);

        restDeviceGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeviceGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeviceGroup))
            )
            .andExpect(status().isOk());

        // Validate the DeviceGroup in the database
        List<DeviceGroup> deviceGroupList = deviceGroupRepository.findAll();
        assertThat(deviceGroupList).hasSize(databaseSizeBeforeUpdate);
        DeviceGroup testDeviceGroup = deviceGroupList.get(deviceGroupList.size() - 1);
        assertThat(testDeviceGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDeviceGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateDeviceGroupWithPatch() throws Exception {
        // Initialize the database
        deviceGroupRepository.saveAndFlush(deviceGroup);

        int databaseSizeBeforeUpdate = deviceGroupRepository.findAll().size();

        // Update the deviceGroup using partial update
        DeviceGroup partialUpdatedDeviceGroup = new DeviceGroup();
        partialUpdatedDeviceGroup.setId(deviceGroup.getId());

        partialUpdatedDeviceGroup.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restDeviceGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeviceGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeviceGroup))
            )
            .andExpect(status().isOk());

        // Validate the DeviceGroup in the database
        List<DeviceGroup> deviceGroupList = deviceGroupRepository.findAll();
        assertThat(deviceGroupList).hasSize(databaseSizeBeforeUpdate);
        DeviceGroup testDeviceGroup = deviceGroupList.get(deviceGroupList.size() - 1);
        assertThat(testDeviceGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDeviceGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingDeviceGroup() throws Exception {
        int databaseSizeBeforeUpdate = deviceGroupRepository.findAll().size();
        deviceGroup.setId(count.incrementAndGet());

        // Create the DeviceGroup
        DeviceGroupDTO deviceGroupDTO = deviceGroupMapper.toDto(deviceGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviceGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, deviceGroupDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deviceGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeviceGroup in the database
        List<DeviceGroup> deviceGroupList = deviceGroupRepository.findAll();
        assertThat(deviceGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDeviceGroup() throws Exception {
        int databaseSizeBeforeUpdate = deviceGroupRepository.findAll().size();
        deviceGroup.setId(count.incrementAndGet());

        // Create the DeviceGroup
        DeviceGroupDTO deviceGroupDTO = deviceGroupMapper.toDto(deviceGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviceGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deviceGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeviceGroup in the database
        List<DeviceGroup> deviceGroupList = deviceGroupRepository.findAll();
        assertThat(deviceGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDeviceGroup() throws Exception {
        int databaseSizeBeforeUpdate = deviceGroupRepository.findAll().size();
        deviceGroup.setId(count.incrementAndGet());

        // Create the DeviceGroup
        DeviceGroupDTO deviceGroupDTO = deviceGroupMapper.toDto(deviceGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviceGroupMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(deviceGroupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DeviceGroup in the database
        List<DeviceGroup> deviceGroupList = deviceGroupRepository.findAll();
        assertThat(deviceGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDeviceGroup() throws Exception {
        // Initialize the database
        deviceGroupRepository.saveAndFlush(deviceGroup);

        int databaseSizeBeforeDelete = deviceGroupRepository.findAll().size();

        // Delete the deviceGroup
        restDeviceGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, deviceGroup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeviceGroup> deviceGroupList = deviceGroupRepository.findAll();
        assertThat(deviceGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
