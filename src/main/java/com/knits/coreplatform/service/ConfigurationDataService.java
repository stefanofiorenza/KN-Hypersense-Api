package com.knits.coreplatform.service;

import com.knits.coreplatform.service.dto.ConfigurationDataDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.knits.coreplatform.domain.ConfigurationData}.
 */
public interface ConfigurationDataService {
    /**
     * Save a configurationData.
     *
     * @param configurationDataDTO the entity to save.
     * @return the persisted entity.
     */
    ConfigurationDataDTO save(ConfigurationDataDTO configurationDataDTO);

    /**
     * Partially updates a configurationData.
     *
     * @param configurationDataDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ConfigurationDataDTO> partialUpdate(ConfigurationDataDTO configurationDataDTO);

    /**
     * Get all the configurationData.
     *
     * @return the list of entities.
     */
    List<ConfigurationDataDTO> findAll();

    /**
     * Get the "id" configurationData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConfigurationDataDTO> findOne(Long id);

    /**
     * Delete the "id" configurationData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
