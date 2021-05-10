package com.knits.coreplatform.service;

import com.knits.coreplatform.service.dto.RuleConfigurationDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.knits.coreplatform.domain.RuleConfiguration}.
 */
public interface RuleConfigurationService {
    /**
     * Save a ruleConfiguration.
     *
     * @param ruleConfigurationDTO the entity to save.
     * @return the persisted entity.
     */
    RuleConfigurationDTO save(RuleConfigurationDTO ruleConfigurationDTO);

    /**
     * Partially updates a ruleConfiguration.
     *
     * @param ruleConfigurationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RuleConfigurationDTO> partialUpdate(RuleConfigurationDTO ruleConfigurationDTO);

    /**
     * Get all the ruleConfigurations.
     *
     * @return the list of entities.
     */
    List<RuleConfigurationDTO> findAll();

    /**
     * Get the "id" ruleConfiguration.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RuleConfigurationDTO> findOne(Long id);

    /**
     * Delete the "id" ruleConfiguration.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
