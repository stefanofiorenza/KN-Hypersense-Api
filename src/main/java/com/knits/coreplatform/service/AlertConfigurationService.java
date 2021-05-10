package com.knits.coreplatform.service;

import com.knits.coreplatform.service.dto.AlertConfigurationDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.knits.coreplatform.domain.AlertConfiguration}.
 */
public interface AlertConfigurationService {
    /**
     * Save a alertConfiguration.
     *
     * @param alertConfigurationDTO the entity to save.
     * @return the persisted entity.
     */
    AlertConfigurationDTO save(AlertConfigurationDTO alertConfigurationDTO);

    /**
     * Partially updates a alertConfiguration.
     *
     * @param alertConfigurationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AlertConfigurationDTO> partialUpdate(AlertConfigurationDTO alertConfigurationDTO);

    /**
     * Get all the alertConfigurations.
     *
     * @return the list of entities.
     */
    List<AlertConfigurationDTO> findAll();

    /**
     * Get the "id" alertConfiguration.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AlertConfigurationDTO> findOne(Long id);

    /**
     * Delete the "id" alertConfiguration.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
