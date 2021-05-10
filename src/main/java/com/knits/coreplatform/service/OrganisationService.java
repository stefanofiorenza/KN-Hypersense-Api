package com.knits.coreplatform.service;

import com.knits.coreplatform.service.dto.OrganisationDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.knits.coreplatform.domain.Organisation}.
 */
public interface OrganisationService {
    /**
     * Save a organisation.
     *
     * @param organisationDTO the entity to save.
     * @return the persisted entity.
     */
    OrganisationDTO save(OrganisationDTO organisationDTO);

    /**
     * Partially updates a organisation.
     *
     * @param organisationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrganisationDTO> partialUpdate(OrganisationDTO organisationDTO);

    /**
     * Get all the organisations.
     *
     * @return the list of entities.
     */
    List<OrganisationDTO> findAll();

    /**
     * Get the "id" organisation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrganisationDTO> findOne(Long id);

    /**
     * Delete the "id" organisation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
