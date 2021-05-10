package com.knits.coreplatform.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.knits.coreplatform.IntegrationTest;
import com.knits.coreplatform.domain.Telemetry;
import com.knits.coreplatform.repository.TelemetryRepository;
import com.knits.coreplatform.service.dto.TelemetryDTO;
import com.knits.coreplatform.service.mapper.TelemetryMapper;
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
 * Integration tests for the {@link TelemetryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TelemetryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DATA = "AAAAAAAAAA";
    private static final String UPDATED_DATA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/telemetries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TelemetryRepository telemetryRepository;

    @Autowired
    private TelemetryMapper telemetryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTelemetryMockMvc;

    private Telemetry telemetry;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Telemetry createEntity(EntityManager em) {
        Telemetry telemetry = new Telemetry().name(DEFAULT_NAME).data(DEFAULT_DATA);
        return telemetry;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Telemetry createUpdatedEntity(EntityManager em) {
        Telemetry telemetry = new Telemetry().name(UPDATED_NAME).data(UPDATED_DATA);
        return telemetry;
    }

    @BeforeEach
    public void initTest() {
        telemetry = createEntity(em);
    }

    @Test
    @Transactional
    void createTelemetry() throws Exception {
        int databaseSizeBeforeCreate = telemetryRepository.findAll().size();
        // Create the Telemetry
        TelemetryDTO telemetryDTO = telemetryMapper.toDto(telemetry);
        restTelemetryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(telemetryDTO)))
            .andExpect(status().isCreated());

        // Validate the Telemetry in the database
        List<Telemetry> telemetryList = telemetryRepository.findAll();
        assertThat(telemetryList).hasSize(databaseSizeBeforeCreate + 1);
        Telemetry testTelemetry = telemetryList.get(telemetryList.size() - 1);
        assertThat(testTelemetry.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTelemetry.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    @Transactional
    void createTelemetryWithExistingId() throws Exception {
        // Create the Telemetry with an existing ID
        telemetry.setId(1L);
        TelemetryDTO telemetryDTO = telemetryMapper.toDto(telemetry);

        int databaseSizeBeforeCreate = telemetryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTelemetryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(telemetryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Telemetry in the database
        List<Telemetry> telemetryList = telemetryRepository.findAll();
        assertThat(telemetryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTelemetries() throws Exception {
        // Initialize the database
        telemetryRepository.saveAndFlush(telemetry);

        // Get all the telemetryList
        restTelemetryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(telemetry.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA)));
    }

    @Test
    @Transactional
    void getTelemetry() throws Exception {
        // Initialize the database
        telemetryRepository.saveAndFlush(telemetry);

        // Get the telemetry
        restTelemetryMockMvc
            .perform(get(ENTITY_API_URL_ID, telemetry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(telemetry.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA));
    }

    @Test
    @Transactional
    void getNonExistingTelemetry() throws Exception {
        // Get the telemetry
        restTelemetryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTelemetry() throws Exception {
        // Initialize the database
        telemetryRepository.saveAndFlush(telemetry);

        int databaseSizeBeforeUpdate = telemetryRepository.findAll().size();

        // Update the telemetry
        Telemetry updatedTelemetry = telemetryRepository.findById(telemetry.getId()).get();
        // Disconnect from session so that the updates on updatedTelemetry are not directly saved in db
        em.detach(updatedTelemetry);
        updatedTelemetry.name(UPDATED_NAME).data(UPDATED_DATA);
        TelemetryDTO telemetryDTO = telemetryMapper.toDto(updatedTelemetry);

        restTelemetryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, telemetryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(telemetryDTO))
            )
            .andExpect(status().isOk());

        // Validate the Telemetry in the database
        List<Telemetry> telemetryList = telemetryRepository.findAll();
        assertThat(telemetryList).hasSize(databaseSizeBeforeUpdate);
        Telemetry testTelemetry = telemetryList.get(telemetryList.size() - 1);
        assertThat(testTelemetry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTelemetry.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    void putNonExistingTelemetry() throws Exception {
        int databaseSizeBeforeUpdate = telemetryRepository.findAll().size();
        telemetry.setId(count.incrementAndGet());

        // Create the Telemetry
        TelemetryDTO telemetryDTO = telemetryMapper.toDto(telemetry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelemetryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, telemetryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(telemetryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Telemetry in the database
        List<Telemetry> telemetryList = telemetryRepository.findAll();
        assertThat(telemetryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTelemetry() throws Exception {
        int databaseSizeBeforeUpdate = telemetryRepository.findAll().size();
        telemetry.setId(count.incrementAndGet());

        // Create the Telemetry
        TelemetryDTO telemetryDTO = telemetryMapper.toDto(telemetry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelemetryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(telemetryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Telemetry in the database
        List<Telemetry> telemetryList = telemetryRepository.findAll();
        assertThat(telemetryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTelemetry() throws Exception {
        int databaseSizeBeforeUpdate = telemetryRepository.findAll().size();
        telemetry.setId(count.incrementAndGet());

        // Create the Telemetry
        TelemetryDTO telemetryDTO = telemetryMapper.toDto(telemetry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelemetryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(telemetryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Telemetry in the database
        List<Telemetry> telemetryList = telemetryRepository.findAll();
        assertThat(telemetryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTelemetryWithPatch() throws Exception {
        // Initialize the database
        telemetryRepository.saveAndFlush(telemetry);

        int databaseSizeBeforeUpdate = telemetryRepository.findAll().size();

        // Update the telemetry using partial update
        Telemetry partialUpdatedTelemetry = new Telemetry();
        partialUpdatedTelemetry.setId(telemetry.getId());

        partialUpdatedTelemetry.name(UPDATED_NAME);

        restTelemetryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTelemetry.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTelemetry))
            )
            .andExpect(status().isOk());

        // Validate the Telemetry in the database
        List<Telemetry> telemetryList = telemetryRepository.findAll();
        assertThat(telemetryList).hasSize(databaseSizeBeforeUpdate);
        Telemetry testTelemetry = telemetryList.get(telemetryList.size() - 1);
        assertThat(testTelemetry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTelemetry.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    @Transactional
    void fullUpdateTelemetryWithPatch() throws Exception {
        // Initialize the database
        telemetryRepository.saveAndFlush(telemetry);

        int databaseSizeBeforeUpdate = telemetryRepository.findAll().size();

        // Update the telemetry using partial update
        Telemetry partialUpdatedTelemetry = new Telemetry();
        partialUpdatedTelemetry.setId(telemetry.getId());

        partialUpdatedTelemetry.name(UPDATED_NAME).data(UPDATED_DATA);

        restTelemetryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTelemetry.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTelemetry))
            )
            .andExpect(status().isOk());

        // Validate the Telemetry in the database
        List<Telemetry> telemetryList = telemetryRepository.findAll();
        assertThat(telemetryList).hasSize(databaseSizeBeforeUpdate);
        Telemetry testTelemetry = telemetryList.get(telemetryList.size() - 1);
        assertThat(testTelemetry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTelemetry.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    void patchNonExistingTelemetry() throws Exception {
        int databaseSizeBeforeUpdate = telemetryRepository.findAll().size();
        telemetry.setId(count.incrementAndGet());

        // Create the Telemetry
        TelemetryDTO telemetryDTO = telemetryMapper.toDto(telemetry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelemetryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, telemetryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(telemetryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Telemetry in the database
        List<Telemetry> telemetryList = telemetryRepository.findAll();
        assertThat(telemetryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTelemetry() throws Exception {
        int databaseSizeBeforeUpdate = telemetryRepository.findAll().size();
        telemetry.setId(count.incrementAndGet());

        // Create the Telemetry
        TelemetryDTO telemetryDTO = telemetryMapper.toDto(telemetry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelemetryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(telemetryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Telemetry in the database
        List<Telemetry> telemetryList = telemetryRepository.findAll();
        assertThat(telemetryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTelemetry() throws Exception {
        int databaseSizeBeforeUpdate = telemetryRepository.findAll().size();
        telemetry.setId(count.incrementAndGet());

        // Create the Telemetry
        TelemetryDTO telemetryDTO = telemetryMapper.toDto(telemetry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelemetryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(telemetryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Telemetry in the database
        List<Telemetry> telemetryList = telemetryRepository.findAll();
        assertThat(telemetryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTelemetry() throws Exception {
        // Initialize the database
        telemetryRepository.saveAndFlush(telemetry);

        int databaseSizeBeforeDelete = telemetryRepository.findAll().size();

        // Delete the telemetry
        restTelemetryMockMvc
            .perform(delete(ENTITY_API_URL_ID, telemetry.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Telemetry> telemetryList = telemetryRepository.findAll();
        assertThat(telemetryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
