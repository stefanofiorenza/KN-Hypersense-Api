package com.knits.coreplatform.service;

import com.knits.coreplatform.service.dto.ThingDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.knits.coreplatform.domain.Thing}.
 */
public interface ThingService {
    /**
     * Save a thing.
     *
     * @param thingDTO the entity to save.
     * @return the persisted entity.
     */
    ThingDTO save(ThingDTO thingDTO);

    /**
     * Partially updates a thing.
     *
     * @param thingDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ThingDTO> partialUpdate(ThingDTO thingDTO);

    /**
     * Get all the things.
     *
     * @return the list of entities.
     */
    List<ThingDTO> findAll();

    /**
     * Get the "id" thing.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ThingDTO> findOne(Long id);

    /**
     * Delete the "id" thing.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
