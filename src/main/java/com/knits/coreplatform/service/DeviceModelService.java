package com.knits.coreplatform.service;

import com.knits.coreplatform.service.dto.DeviceModelDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.knits.coreplatform.domain.DeviceModel}.
 */
public interface DeviceModelService {
    /**
     * Save a deviceModel.
     *
     * @param deviceModelDTO the entity to save.
     * @return the persisted entity.
     */
    DeviceModelDTO save(DeviceModelDTO deviceModelDTO);

    /**
     * Partially updates a deviceModel.
     *
     * @param deviceModelDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DeviceModelDTO> partialUpdate(DeviceModelDTO deviceModelDTO);

    /**
     * Get all the deviceModels.
     *
     * @return the list of entities.
     */
    List<DeviceModelDTO> findAll();

    /**
     * Get the "id" deviceModel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DeviceModelDTO> findOne(Long id);

    /**
     * Delete the "id" deviceModel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
