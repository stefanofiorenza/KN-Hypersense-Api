package com.knits.coreplatform.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.knits.coreplatform.IntegrationTest;
import com.knits.coreplatform.domain.ThingCategory;
import com.knits.coreplatform.repository.ThingCategoryRepository;
import com.knits.coreplatform.service.dto.ThingCategoryDTO;
import com.knits.coreplatform.service.mapper.ThingCategoryMapper;
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
 * Integration tests for the {@link ThingCategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ThingCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/thing-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ThingCategoryRepository thingCategoryRepository;

    @Autowired
    private ThingCategoryMapper thingCategoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restThingCategoryMockMvc;

    private ThingCategory thingCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThingCategory createEntity(EntityManager em) {
        ThingCategory thingCategory = new ThingCategory().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return thingCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThingCategory createUpdatedEntity(EntityManager em) {
        ThingCategory thingCategory = new ThingCategory().name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return thingCategory;
    }

    @BeforeEach
    public void initTest() {
        thingCategory = createEntity(em);
    }

    @Test
    @Transactional
    void createThingCategory() throws Exception {
        int databaseSizeBeforeCreate = thingCategoryRepository.findAll().size();
        // Create the ThingCategory
        ThingCategoryDTO thingCategoryDTO = thingCategoryMapper.toDto(thingCategory);
        restThingCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thingCategoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ThingCategory in the database
        List<ThingCategory> thingCategoryList = thingCategoryRepository.findAll();
        assertThat(thingCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        ThingCategory testThingCategory = thingCategoryList.get(thingCategoryList.size() - 1);
        assertThat(testThingCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testThingCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createThingCategoryWithExistingId() throws Exception {
        // Create the ThingCategory with an existing ID
        thingCategory.setId(1L);
        ThingCategoryDTO thingCategoryDTO = thingCategoryMapper.toDto(thingCategory);

        int databaseSizeBeforeCreate = thingCategoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restThingCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thingCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThingCategory in the database
        List<ThingCategory> thingCategoryList = thingCategoryRepository.findAll();
        assertThat(thingCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllThingCategories() throws Exception {
        // Initialize the database
        thingCategoryRepository.saveAndFlush(thingCategory);

        // Get all the thingCategoryList
        restThingCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thingCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getThingCategory() throws Exception {
        // Initialize the database
        thingCategoryRepository.saveAndFlush(thingCategory);

        // Get the thingCategory
        restThingCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, thingCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(thingCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingThingCategory() throws Exception {
        // Get the thingCategory
        restThingCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewThingCategory() throws Exception {
        // Initialize the database
        thingCategoryRepository.saveAndFlush(thingCategory);

        int databaseSizeBeforeUpdate = thingCategoryRepository.findAll().size();

        // Update the thingCategory
        ThingCategory updatedThingCategory = thingCategoryRepository.findById(thingCategory.getId()).get();
        // Disconnect from session so that the updates on updatedThingCategory are not directly saved in db
        em.detach(updatedThingCategory);
        updatedThingCategory.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        ThingCategoryDTO thingCategoryDTO = thingCategoryMapper.toDto(updatedThingCategory);

        restThingCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, thingCategoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thingCategoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the ThingCategory in the database
        List<ThingCategory> thingCategoryList = thingCategoryRepository.findAll();
        assertThat(thingCategoryList).hasSize(databaseSizeBeforeUpdate);
        ThingCategory testThingCategory = thingCategoryList.get(thingCategoryList.size() - 1);
        assertThat(testThingCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testThingCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingThingCategory() throws Exception {
        int databaseSizeBeforeUpdate = thingCategoryRepository.findAll().size();
        thingCategory.setId(count.incrementAndGet());

        // Create the ThingCategory
        ThingCategoryDTO thingCategoryDTO = thingCategoryMapper.toDto(thingCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThingCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, thingCategoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thingCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThingCategory in the database
        List<ThingCategory> thingCategoryList = thingCategoryRepository.findAll();
        assertThat(thingCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchThingCategory() throws Exception {
        int databaseSizeBeforeUpdate = thingCategoryRepository.findAll().size();
        thingCategory.setId(count.incrementAndGet());

        // Create the ThingCategory
        ThingCategoryDTO thingCategoryDTO = thingCategoryMapper.toDto(thingCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThingCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thingCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThingCategory in the database
        List<ThingCategory> thingCategoryList = thingCategoryRepository.findAll();
        assertThat(thingCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamThingCategory() throws Exception {
        int databaseSizeBeforeUpdate = thingCategoryRepository.findAll().size();
        thingCategory.setId(count.incrementAndGet());

        // Create the ThingCategory
        ThingCategoryDTO thingCategoryDTO = thingCategoryMapper.toDto(thingCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThingCategoryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thingCategoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ThingCategory in the database
        List<ThingCategory> thingCategoryList = thingCategoryRepository.findAll();
        assertThat(thingCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateThingCategoryWithPatch() throws Exception {
        // Initialize the database
        thingCategoryRepository.saveAndFlush(thingCategory);

        int databaseSizeBeforeUpdate = thingCategoryRepository.findAll().size();

        // Update the thingCategory using partial update
        ThingCategory partialUpdatedThingCategory = new ThingCategory();
        partialUpdatedThingCategory.setId(thingCategory.getId());

        restThingCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThingCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedThingCategory))
            )
            .andExpect(status().isOk());

        // Validate the ThingCategory in the database
        List<ThingCategory> thingCategoryList = thingCategoryRepository.findAll();
        assertThat(thingCategoryList).hasSize(databaseSizeBeforeUpdate);
        ThingCategory testThingCategory = thingCategoryList.get(thingCategoryList.size() - 1);
        assertThat(testThingCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testThingCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateThingCategoryWithPatch() throws Exception {
        // Initialize the database
        thingCategoryRepository.saveAndFlush(thingCategory);

        int databaseSizeBeforeUpdate = thingCategoryRepository.findAll().size();

        // Update the thingCategory using partial update
        ThingCategory partialUpdatedThingCategory = new ThingCategory();
        partialUpdatedThingCategory.setId(thingCategory.getId());

        partialUpdatedThingCategory.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restThingCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThingCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedThingCategory))
            )
            .andExpect(status().isOk());

        // Validate the ThingCategory in the database
        List<ThingCategory> thingCategoryList = thingCategoryRepository.findAll();
        assertThat(thingCategoryList).hasSize(databaseSizeBeforeUpdate);
        ThingCategory testThingCategory = thingCategoryList.get(thingCategoryList.size() - 1);
        assertThat(testThingCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testThingCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingThingCategory() throws Exception {
        int databaseSizeBeforeUpdate = thingCategoryRepository.findAll().size();
        thingCategory.setId(count.incrementAndGet());

        // Create the ThingCategory
        ThingCategoryDTO thingCategoryDTO = thingCategoryMapper.toDto(thingCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThingCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, thingCategoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(thingCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThingCategory in the database
        List<ThingCategory> thingCategoryList = thingCategoryRepository.findAll();
        assertThat(thingCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchThingCategory() throws Exception {
        int databaseSizeBeforeUpdate = thingCategoryRepository.findAll().size();
        thingCategory.setId(count.incrementAndGet());

        // Create the ThingCategory
        ThingCategoryDTO thingCategoryDTO = thingCategoryMapper.toDto(thingCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThingCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(thingCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThingCategory in the database
        List<ThingCategory> thingCategoryList = thingCategoryRepository.findAll();
        assertThat(thingCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamThingCategory() throws Exception {
        int databaseSizeBeforeUpdate = thingCategoryRepository.findAll().size();
        thingCategory.setId(count.incrementAndGet());

        // Create the ThingCategory
        ThingCategoryDTO thingCategoryDTO = thingCategoryMapper.toDto(thingCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThingCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(thingCategoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ThingCategory in the database
        List<ThingCategory> thingCategoryList = thingCategoryRepository.findAll();
        assertThat(thingCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteThingCategory() throws Exception {
        // Initialize the database
        thingCategoryRepository.saveAndFlush(thingCategory);

        int databaseSizeBeforeDelete = thingCategoryRepository.findAll().size();

        // Delete the thingCategory
        restThingCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, thingCategory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ThingCategory> thingCategoryList = thingCategoryRepository.findAll();
        assertThat(thingCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
