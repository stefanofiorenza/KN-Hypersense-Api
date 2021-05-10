package com.knits.coreplatform.service;

import com.knits.coreplatform.service.dto.AlertMessageDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.knits.coreplatform.domain.AlertMessage}.
 */
public interface AlertMessageService {
    /**
     * Save a alertMessage.
     *
     * @param alertMessageDTO the entity to save.
     * @return the persisted entity.
     */
    AlertMessageDTO save(AlertMessageDTO alertMessageDTO);

    /**
     * Partially updates a alertMessage.
     *
     * @param alertMessageDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AlertMessageDTO> partialUpdate(AlertMessageDTO alertMessageDTO);

    /**
     * Get all the alertMessages.
     *
     * @return the list of entities.
     */
    List<AlertMessageDTO> findAll();

    /**
     * Get the "id" alertMessage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AlertMessageDTO> findOne(Long id);

    /**
     * Delete the "id" alertMessage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
