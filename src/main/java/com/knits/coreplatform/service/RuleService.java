package com.knits.coreplatform.service;

import com.knits.coreplatform.service.dto.RuleDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.knits.coreplatform.domain.Rule}.
 */
public interface RuleService {
    /**
     * Save a rule.
     *
     * @param ruleDTO the entity to save.
     * @return the persisted entity.
     */
    RuleDTO save(RuleDTO ruleDTO);

    /**
     * Partially updates a rule.
     *
     * @param ruleDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RuleDTO> partialUpdate(RuleDTO ruleDTO);

    /**
     * Get all the rules.
     *
     * @return the list of entities.
     */
    List<RuleDTO> findAll();

    /**
     * Get the "id" rule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RuleDTO> findOne(Long id);

    /**
     * Delete the "id" rule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
