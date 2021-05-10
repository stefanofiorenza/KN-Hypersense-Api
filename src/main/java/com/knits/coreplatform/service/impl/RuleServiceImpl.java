package com.knits.coreplatform.service.impl;

import com.knits.coreplatform.domain.Rule;
import com.knits.coreplatform.repository.RuleRepository;
import com.knits.coreplatform.service.RuleService;
import com.knits.coreplatform.service.dto.RuleDTO;
import com.knits.coreplatform.service.mapper.RuleMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Rule}.
 */
@Service
@Transactional
public class RuleServiceImpl implements RuleService {

    private final Logger log = LoggerFactory.getLogger(RuleServiceImpl.class);

    private final RuleRepository ruleRepository;

    private final RuleMapper ruleMapper;

    public RuleServiceImpl(RuleRepository ruleRepository, RuleMapper ruleMapper) {
        this.ruleRepository = ruleRepository;
        this.ruleMapper = ruleMapper;
    }

    @Override
    public RuleDTO save(RuleDTO ruleDTO) {
        log.debug("Request to save Rule : {}", ruleDTO);
        Rule rule = ruleMapper.toEntity(ruleDTO);
        rule = ruleRepository.save(rule);
        return ruleMapper.toDto(rule);
    }

    @Override
    public Optional<RuleDTO> partialUpdate(RuleDTO ruleDTO) {
        log.debug("Request to partially update Rule : {}", ruleDTO);

        return ruleRepository
            .findById(ruleDTO.getId())
            .map(
                existingRule -> {
                    ruleMapper.partialUpdate(existingRule, ruleDTO);
                    return existingRule;
                }
            )
            .map(ruleRepository::save)
            .map(ruleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RuleDTO> findAll() {
        log.debug("Request to get all Rules");
        return ruleRepository.findAll().stream().map(ruleMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RuleDTO> findOne(Long id) {
        log.debug("Request to get Rule : {}", id);
        return ruleRepository.findById(id).map(ruleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rule : {}", id);
        ruleRepository.deleteById(id);
    }
}
