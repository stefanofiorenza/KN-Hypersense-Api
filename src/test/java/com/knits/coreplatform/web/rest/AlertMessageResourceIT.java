package com.knits.coreplatform.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.knits.coreplatform.IntegrationTest;
import com.knits.coreplatform.domain.AlertMessage;
import com.knits.coreplatform.repository.AlertMessageRepository;
import com.knits.coreplatform.service.dto.AlertMessageDTO;
import com.knits.coreplatform.service.mapper.AlertMessageMapper;
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
 * Integration tests for the {@link AlertMessageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AlertMessageResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DATA_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/alert-messages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AlertMessageRepository alertMessageRepository;

    @Autowired
    private AlertMessageMapper alertMessageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlertMessageMockMvc;

    private AlertMessage alertMessage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlertMessage createEntity(EntityManager em) {
        AlertMessage alertMessage = new AlertMessage()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .dataType(DEFAULT_DATA_TYPE)
            .value(DEFAULT_VALUE);
        return alertMessage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlertMessage createUpdatedEntity(EntityManager em) {
        AlertMessage alertMessage = new AlertMessage()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .dataType(UPDATED_DATA_TYPE)
            .value(UPDATED_VALUE);
        return alertMessage;
    }

    @BeforeEach
    public void initTest() {
        alertMessage = createEntity(em);
    }

    @Test
    @Transactional
    void createAlertMessage() throws Exception {
        int databaseSizeBeforeCreate = alertMessageRepository.findAll().size();
        // Create the AlertMessage
        AlertMessageDTO alertMessageDTO = alertMessageMapper.toDto(alertMessage);
        restAlertMessageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alertMessageDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AlertMessage in the database
        List<AlertMessage> alertMessageList = alertMessageRepository.findAll();
        assertThat(alertMessageList).hasSize(databaseSizeBeforeCreate + 1);
        AlertMessage testAlertMessage = alertMessageList.get(alertMessageList.size() - 1);
        assertThat(testAlertMessage.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAlertMessage.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAlertMessage.getDataType()).isEqualTo(DEFAULT_DATA_TYPE);
        assertThat(testAlertMessage.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void createAlertMessageWithExistingId() throws Exception {
        // Create the AlertMessage with an existing ID
        alertMessage.setId(1L);
        AlertMessageDTO alertMessageDTO = alertMessageMapper.toDto(alertMessage);

        int databaseSizeBeforeCreate = alertMessageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlertMessageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alertMessageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlertMessage in the database
        List<AlertMessage> alertMessageList = alertMessageRepository.findAll();
        assertThat(alertMessageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAlertMessages() throws Exception {
        // Initialize the database
        alertMessageRepository.saveAndFlush(alertMessage);

        // Get all the alertMessageList
        restAlertMessageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alertMessage.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].dataType").value(hasItem(DEFAULT_DATA_TYPE)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    void getAlertMessage() throws Exception {
        // Initialize the database
        alertMessageRepository.saveAndFlush(alertMessage);

        // Get the alertMessage
        restAlertMessageMockMvc
            .perform(get(ENTITY_API_URL_ID, alertMessage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alertMessage.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.dataType").value(DEFAULT_DATA_TYPE))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingAlertMessage() throws Exception {
        // Get the alertMessage
        restAlertMessageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAlertMessage() throws Exception {
        // Initialize the database
        alertMessageRepository.saveAndFlush(alertMessage);

        int databaseSizeBeforeUpdate = alertMessageRepository.findAll().size();

        // Update the alertMessage
        AlertMessage updatedAlertMessage = alertMessageRepository.findById(alertMessage.getId()).get();
        // Disconnect from session so that the updates on updatedAlertMessage are not directly saved in db
        em.detach(updatedAlertMessage);
        updatedAlertMessage.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).dataType(UPDATED_DATA_TYPE).value(UPDATED_VALUE);
        AlertMessageDTO alertMessageDTO = alertMessageMapper.toDto(updatedAlertMessage);

        restAlertMessageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, alertMessageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alertMessageDTO))
            )
            .andExpect(status().isOk());

        // Validate the AlertMessage in the database
        List<AlertMessage> alertMessageList = alertMessageRepository.findAll();
        assertThat(alertMessageList).hasSize(databaseSizeBeforeUpdate);
        AlertMessage testAlertMessage = alertMessageList.get(alertMessageList.size() - 1);
        assertThat(testAlertMessage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAlertMessage.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAlertMessage.getDataType()).isEqualTo(UPDATED_DATA_TYPE);
        assertThat(testAlertMessage.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingAlertMessage() throws Exception {
        int databaseSizeBeforeUpdate = alertMessageRepository.findAll().size();
        alertMessage.setId(count.incrementAndGet());

        // Create the AlertMessage
        AlertMessageDTO alertMessageDTO = alertMessageMapper.toDto(alertMessage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlertMessageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, alertMessageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alertMessageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlertMessage in the database
        List<AlertMessage> alertMessageList = alertMessageRepository.findAll();
        assertThat(alertMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAlertMessage() throws Exception {
        int databaseSizeBeforeUpdate = alertMessageRepository.findAll().size();
        alertMessage.setId(count.incrementAndGet());

        // Create the AlertMessage
        AlertMessageDTO alertMessageDTO = alertMessageMapper.toDto(alertMessage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlertMessageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alertMessageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlertMessage in the database
        List<AlertMessage> alertMessageList = alertMessageRepository.findAll();
        assertThat(alertMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAlertMessage() throws Exception {
        int databaseSizeBeforeUpdate = alertMessageRepository.findAll().size();
        alertMessage.setId(count.incrementAndGet());

        // Create the AlertMessage
        AlertMessageDTO alertMessageDTO = alertMessageMapper.toDto(alertMessage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlertMessageMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alertMessageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AlertMessage in the database
        List<AlertMessage> alertMessageList = alertMessageRepository.findAll();
        assertThat(alertMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAlertMessageWithPatch() throws Exception {
        // Initialize the database
        alertMessageRepository.saveAndFlush(alertMessage);

        int databaseSizeBeforeUpdate = alertMessageRepository.findAll().size();

        // Update the alertMessage using partial update
        AlertMessage partialUpdatedAlertMessage = new AlertMessage();
        partialUpdatedAlertMessage.setId(alertMessage.getId());

        partialUpdatedAlertMessage.description(UPDATED_DESCRIPTION).value(UPDATED_VALUE);

        restAlertMessageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlertMessage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAlertMessage))
            )
            .andExpect(status().isOk());

        // Validate the AlertMessage in the database
        List<AlertMessage> alertMessageList = alertMessageRepository.findAll();
        assertThat(alertMessageList).hasSize(databaseSizeBeforeUpdate);
        AlertMessage testAlertMessage = alertMessageList.get(alertMessageList.size() - 1);
        assertThat(testAlertMessage.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAlertMessage.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAlertMessage.getDataType()).isEqualTo(DEFAULT_DATA_TYPE);
        assertThat(testAlertMessage.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateAlertMessageWithPatch() throws Exception {
        // Initialize the database
        alertMessageRepository.saveAndFlush(alertMessage);

        int databaseSizeBeforeUpdate = alertMessageRepository.findAll().size();

        // Update the alertMessage using partial update
        AlertMessage partialUpdatedAlertMessage = new AlertMessage();
        partialUpdatedAlertMessage.setId(alertMessage.getId());

        partialUpdatedAlertMessage.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).dataType(UPDATED_DATA_TYPE).value(UPDATED_VALUE);

        restAlertMessageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlertMessage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAlertMessage))
            )
            .andExpect(status().isOk());

        // Validate the AlertMessage in the database
        List<AlertMessage> alertMessageList = alertMessageRepository.findAll();
        assertThat(alertMessageList).hasSize(databaseSizeBeforeUpdate);
        AlertMessage testAlertMessage = alertMessageList.get(alertMessageList.size() - 1);
        assertThat(testAlertMessage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAlertMessage.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAlertMessage.getDataType()).isEqualTo(UPDATED_DATA_TYPE);
        assertThat(testAlertMessage.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingAlertMessage() throws Exception {
        int databaseSizeBeforeUpdate = alertMessageRepository.findAll().size();
        alertMessage.setId(count.incrementAndGet());

        // Create the AlertMessage
        AlertMessageDTO alertMessageDTO = alertMessageMapper.toDto(alertMessage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlertMessageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, alertMessageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alertMessageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlertMessage in the database
        List<AlertMessage> alertMessageList = alertMessageRepository.findAll();
        assertThat(alertMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAlertMessage() throws Exception {
        int databaseSizeBeforeUpdate = alertMessageRepository.findAll().size();
        alertMessage.setId(count.incrementAndGet());

        // Create the AlertMessage
        AlertMessageDTO alertMessageDTO = alertMessageMapper.toDto(alertMessage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlertMessageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alertMessageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlertMessage in the database
        List<AlertMessage> alertMessageList = alertMessageRepository.findAll();
        assertThat(alertMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAlertMessage() throws Exception {
        int databaseSizeBeforeUpdate = alertMessageRepository.findAll().size();
        alertMessage.setId(count.incrementAndGet());

        // Create the AlertMessage
        AlertMessageDTO alertMessageDTO = alertMessageMapper.toDto(alertMessage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlertMessageMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alertMessageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AlertMessage in the database
        List<AlertMessage> alertMessageList = alertMessageRepository.findAll();
        assertThat(alertMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAlertMessage() throws Exception {
        // Initialize the database
        alertMessageRepository.saveAndFlush(alertMessage);

        int databaseSizeBeforeDelete = alertMessageRepository.findAll().size();

        // Delete the alertMessage
        restAlertMessageMockMvc
            .perform(delete(ENTITY_API_URL_ID, alertMessage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AlertMessage> alertMessageList = alertMessageRepository.findAll();
        assertThat(alertMessageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
