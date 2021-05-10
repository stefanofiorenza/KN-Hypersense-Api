package com.knits.coreplatform.service;

import com.knits.coreplatform.service.dto.DeviceGroupDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.knits.coreplatform.domain.DeviceGroup}.
 */
public interface DeviceGroupService {
    /**
     * Save a deviceGroup.
     *
     * @param deviceGroupDTO the entity to save.
     * @return the persisted entity.
     */
    DeviceGroupDTO save(DeviceGroupDTO deviceGroupDTO);

    /**
     * Partially updates a deviceGroup.
     *
     * @param deviceGroupDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DeviceGroupDTO> partialUpdate(DeviceGroupDTO deviceGroupDTO);

    /**
     * Get all the deviceGroups.
     *
     * @return the list of entities.
     */
    List<DeviceGroupDTO> findAll();

    /**
     * Get the "id" deviceGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DeviceGroupDTO> findOne(Long id);

    /**
     * Delete the "id" deviceGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
