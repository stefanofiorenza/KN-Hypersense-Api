package com.knits.coreplatform.service.impl;

import com.knits.coreplatform.domain.AlertConfiguration;
import com.knits.coreplatform.repository.AlertConfigurationRepository;
import com.knits.coreplatform.service.AlertConfigurationService;
import com.knits.coreplatform.service.dto.AlertConfigurationDTO;
import com.knits.coreplatform.service.mapper.AlertConfigurationMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AlertConfiguration}.
 */
@Service
@Transactional
public class AlertConfigurationServiceImpl implements AlertConfigurationService {

    private final Logger log = LoggerFactory.getLogger(AlertConfigurationServiceImpl.class);

    private final AlertConfigurationRepository alertConfigurationRepository;

    private final AlertConfigurationMapper alertConfigurationMapper;

    public AlertConfigurationServiceImpl(
        AlertConfigurationRepository alertConfigurationRepository,
        AlertConfigurationMapper alertConfigurationMapper
    ) {
        this.alertConfigurationRepository = alertConfigurationRepository;
        this.alertConfigurationMapper = alertConfigurationMapper;
    }

    @Override
    public AlertConfigurationDTO save(AlertConfigurationDTO alertConfigurationDTO) {
        log.debug("Request to save AlertConfiguration : {}", alertConfigurationDTO);
        AlertConfiguration alertConfiguration = alertConfigurationMapper.toEntity(alertConfigurationDTO);
        alertConfiguration = alertConfigurationRepository.save(alertConfiguration);
        return alertConfigurationMapper.toDto(alertConfiguration);
    }

    @Override
    public Optional<AlertConfigurationDTO> partialUpdate(AlertConfigurationDTO alertConfigurationDTO) {
        log.debug("Request to partially update AlertConfiguration : {}", alertConfigurationDTO);

        return alertConfigurationRepository
            .findById(alertConfigurationDTO.getId())
            .map(
                existingAlertConfiguration -> {
                    alertConfigurationMapper.partialUpdate(existingAlertConfiguration, alertConfigurationDTO);
                    return existingAlertConfiguration;
                }
            )
            .map(alertConfigurationRepository::save)
            .map(alertConfigurationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlertConfigurationDTO> findAll() {
        log.debug("Request to get all AlertConfigurations");
        return alertConfigurationRepository
            .findAll()
            .stream()
            .map(alertConfigurationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AlertConfigurationDTO> findOne(Long id) {
        log.debug("Request to get AlertConfiguration : {}", id);
        return alertConfigurationRepository.findById(id).map(alertConfigurationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AlertConfiguration : {}", id);
        alertConfigurationRepository.deleteById(id);
    }
}
