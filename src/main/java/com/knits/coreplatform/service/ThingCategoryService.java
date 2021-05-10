package com.knits.coreplatform.service;

import com.knits.coreplatform.service.dto.ThingCategoryDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.knits.coreplatform.domain.ThingCategory}.
 */
public interface ThingCategoryService {
    /**
     * Save a thingCategory.
     *
     * @param thingCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    ThingCategoryDTO save(ThingCategoryDTO thingCategoryDTO);

    /**
     * Partially updates a thingCategory.
     *
     * @param thingCategoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ThingCategoryDTO> partialUpdate(ThingCategoryDTO thingCategoryDTO);

    /**
     * Get all the thingCategories.
     *
     * @return the list of entities.
     */
    List<ThingCategoryDTO> findAll();

    /**
     * Get the "id" thingCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ThingCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" thingCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
