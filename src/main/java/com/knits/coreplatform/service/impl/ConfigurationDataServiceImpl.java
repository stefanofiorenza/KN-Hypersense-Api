package com.knits.coreplatform.service.impl;

import com.knits.coreplatform.domain.ConfigurationData;
import com.knits.coreplatform.repository.ConfigurationDataRepository;
import com.knits.coreplatform.service.ConfigurationDataService;
import com.knits.coreplatform.service.dto.ConfigurationDataDTO;
import com.knits.coreplatform.service.mapper.ConfigurationDataMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ConfigurationData}.
 */
@Service
@Transactional
public class ConfigurationDataServiceImpl implements ConfigurationDataService {

    private final Logger log = LoggerFactory.getLogger(ConfigurationDataServiceImpl.class);

    private final ConfigurationDataRepository configurationDataRepository;

    private final ConfigurationDataMapper configurationDataMapper;

    public ConfigurationDataServiceImpl(
        ConfigurationDataRepository configurationDataRepository,
        ConfigurationDataMapper configurationDataMapper
    ) {
        this.configurationDataRepository = configurationDataRepository;
        this.configurationDataMapper = configurationDataMapper;
    }

    @Override
    public ConfigurationDataDTO save(ConfigurationDataDTO configurationDataDTO) {
        log.debug("Request to save ConfigurationData : {}", configurationDataDTO);
        ConfigurationData configurationData = configurationDataMapper.toEntity(configurationDataDTO);
        configurationData = configurationDataRepository.save(configurationData);
        return configurationDataMapper.toDto(configurationData);
    }

    @Override
    public Optional<ConfigurationDataDTO> partialUpdate(ConfigurationDataDTO configurationDataDTO) {
        log.debug("Request to partially update ConfigurationData : {}", configurationDataDTO);

        return configurationDataRepository
            .findById(configurationDataDTO.getId())
            .map(
                existingConfigurationData -> {
                    configurationDataMapper.partialUpdate(existingConfigurationData, configurationDataDTO);
                    return existingConfigurationData;
                }
            )
            .map(configurationDataRepository::save)
            .map(configurationDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConfigurationDataDTO> findAll() {
        log.debug("Request to get all ConfigurationData");
        return configurationDataRepository
            .findAll()
            .stream()
            .map(configurationDataMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ConfigurationDataDTO> findOne(Long id) {
        log.debug("Request to get ConfigurationData : {}", id);
        return configurationDataRepository.findById(id).map(configurationDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConfigurationData : {}", id);
        configurationDataRepository.deleteById(id);
    }
}
