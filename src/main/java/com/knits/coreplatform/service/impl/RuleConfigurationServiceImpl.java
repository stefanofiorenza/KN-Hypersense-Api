package com.knits.coreplatform.service.impl;

import com.knits.coreplatform.domain.RuleConfiguration;
import com.knits.coreplatform.repository.RuleConfigurationRepository;
import com.knits.coreplatform.service.RuleConfigurationService;
import com.knits.coreplatform.service.dto.RuleConfigurationDTO;
import com.knits.coreplatform.service.mapper.RuleConfigurationMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RuleConfiguration}.
 */
@Service
@Transactional
public class RuleConfigurationServiceImpl implements RuleConfigurationService {

    private final Logger log = LoggerFactory.getLogger(RuleConfigurationServiceImpl.class);

    private final RuleConfigurationRepository ruleConfigurationRepository;

    private final RuleConfigurationMapper ruleConfigurationMapper;

    public RuleConfigurationServiceImpl(
        RuleConfigurationRepository ruleConfigurationRepository,
        RuleConfigurationMapper ruleConfigurationMapper
    ) {
        this.ruleConfigurationRepository = ruleConfigurationRepository;
        this.ruleConfigurationMapper = ruleConfigurationMapper;
    }

    @Override
    public RuleConfigurationDTO save(RuleConfigurationDTO ruleConfigurationDTO) {
        log.debug("Request to save RuleConfiguration : {}", ruleConfigurationDTO);
        RuleConfiguration ruleConfiguration = ruleConfigurationMapper.toEntity(ruleConfigurationDTO);
        ruleConfiguration = ruleConfigurationRepository.save(ruleConfiguration);
        return ruleConfigurationMapper.toDto(ruleConfiguration);
    }

    @Override
    public Optional<RuleConfigurationDTO> partialUpdate(RuleConfigurationDTO ruleConfigurationDTO) {
        log.debug("Request to partially update RuleConfiguration : {}", ruleConfigurationDTO);

        return ruleConfigurationRepository
            .findById(ruleConfigurationDTO.getId())
            .map(
                existingRuleConfiguration -> {
                    ruleConfigurationMapper.partialUpdate(existingRuleConfiguration, ruleConfigurationDTO);
                    return existingRuleConfiguration;
                }
            )
            .map(ruleConfigurationRepository::save)
            .map(ruleConfigurationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RuleConfigurationDTO> findAll() {
        log.debug("Request to get all RuleConfigurations");
        return ruleConfigurationRepository
            .findAll()
            .stream()
            .map(ruleConfigurationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RuleConfigurationDTO> findOne(Long id) {
        log.debug("Request to get RuleConfiguration : {}", id);
        return ruleConfigurationRepository.findById(id).map(ruleConfigurationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RuleConfiguration : {}", id);
        ruleConfigurationRepository.deleteById(id);
    }
}
