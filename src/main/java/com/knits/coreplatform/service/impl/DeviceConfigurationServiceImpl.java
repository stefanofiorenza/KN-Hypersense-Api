package com.knits.coreplatform.service.impl;

import com.knits.coreplatform.domain.DeviceConfiguration;
import com.knits.coreplatform.repository.DeviceConfigurationRepository;
import com.knits.coreplatform.service.DeviceConfigurationService;
import com.knits.coreplatform.service.dto.DeviceConfigurationDTO;
import com.knits.coreplatform.service.mapper.DeviceConfigurationMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DeviceConfiguration}.
 */
@Service
@Transactional
public class DeviceConfigurationServiceImpl implements DeviceConfigurationService {

    private final Logger log = LoggerFactory.getLogger(DeviceConfigurationServiceImpl.class);

    private final DeviceConfigurationRepository deviceConfigurationRepository;

    private final DeviceConfigurationMapper deviceConfigurationMapper;

    public DeviceConfigurationServiceImpl(
        DeviceConfigurationRepository deviceConfigurationRepository,
        DeviceConfigurationMapper deviceConfigurationMapper
    ) {
        this.deviceConfigurationRepository = deviceConfigurationRepository;
        this.deviceConfigurationMapper = deviceConfigurationMapper;
    }

    @Override
    public DeviceConfigurationDTO save(DeviceConfigurationDTO deviceConfigurationDTO) {
        log.debug("Request to save DeviceConfiguration : {}", deviceConfigurationDTO);
        DeviceConfiguration deviceConfiguration = deviceConfigurationMapper.toEntity(deviceConfigurationDTO);
        deviceConfiguration = deviceConfigurationRepository.save(deviceConfiguration);
        return deviceConfigurationMapper.toDto(deviceConfiguration);
    }

    @Override
    public Optional<DeviceConfigurationDTO> partialUpdate(DeviceConfigurationDTO deviceConfigurationDTO) {
        log.debug("Request to partially update DeviceConfiguration : {}", deviceConfigurationDTO);

        return deviceConfigurationRepository
            .findById(deviceConfigurationDTO.getId())
            .map(
                existingDeviceConfiguration -> {
                    deviceConfigurationMapper.partialUpdate(existingDeviceConfiguration, deviceConfigurationDTO);
                    return existingDeviceConfiguration;
                }
            )
            .map(deviceConfigurationRepository::save)
            .map(deviceConfigurationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeviceConfigurationDTO> findAll() {
        log.debug("Request to get all DeviceConfigurations");
        return deviceConfigurationRepository
            .findAll()
            .stream()
            .map(deviceConfigurationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DeviceConfigurationDTO> findOne(Long id) {
        log.debug("Request to get DeviceConfiguration : {}", id);
        return deviceConfigurationRepository.findById(id).map(deviceConfigurationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DeviceConfiguration : {}", id);
        deviceConfigurationRepository.deleteById(id);
    }
}
