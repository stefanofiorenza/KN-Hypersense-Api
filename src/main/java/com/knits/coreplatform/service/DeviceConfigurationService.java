package com.knits.coreplatform.service;

import com.knits.coreplatform.service.dto.DeviceConfigurationDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.knits.coreplatform.domain.DeviceConfiguration}.
 */
public interface DeviceConfigurationService {
    /**
     * Save a deviceConfiguration.
     *
     * @param deviceConfigurationDTO the entity to save.
     * @return the persisted entity.
     */
    DeviceConfigurationDTO save(DeviceConfigurationDTO deviceConfigurationDTO);

    /**
     * Partially updates a deviceConfiguration.
     *
     * @param deviceConfigurationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DeviceConfigurationDTO> partialUpdate(DeviceConfigurationDTO deviceConfigurationDTO);

    /**
     * Get all the deviceConfigurations.
     *
     * @return the list of entities.
     */
    List<DeviceConfigurationDTO> findAll();

    /**
     * Get the "id" deviceConfiguration.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DeviceConfigurationDTO> findOne(Long id);

    /**
     * Delete the "id" deviceConfiguration.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
