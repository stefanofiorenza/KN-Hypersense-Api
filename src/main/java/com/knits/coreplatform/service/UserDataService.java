package com.knits.coreplatform.service;

import com.knits.coreplatform.service.dto.UserDataDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.knits.coreplatform.domain.UserData}.
 */
public interface UserDataService {
    /**
     * Save a userData.
     *
     * @param userDataDTO the entity to save.
     * @return the persisted entity.
     */
    UserDataDTO save(UserDataDTO userDataDTO);

    /**
     * Partially updates a userData.
     *
     * @param userDataDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserDataDTO> partialUpdate(UserDataDTO userDataDTO);

    /**
     * Get all the userData.
     *
     * @return the list of entities.
     */
    List<UserDataDTO> findAll();

    /**
     * Get the "id" userData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserDataDTO> findOne(Long id);

    /**
     * Delete the "id" userData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
