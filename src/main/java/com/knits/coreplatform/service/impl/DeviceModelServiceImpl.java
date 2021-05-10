package com.knits.coreplatform.service.impl;

import com.knits.coreplatform.domain.DeviceModel;
import com.knits.coreplatform.repository.DeviceModelRepository;
import com.knits.coreplatform.service.DeviceModelService;
import com.knits.coreplatform.service.dto.DeviceModelDTO;
import com.knits.coreplatform.service.mapper.DeviceModelMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DeviceModel}.
 */
@Service
@Transactional
public class DeviceModelServiceImpl implements DeviceModelService {

    private final Logger log = LoggerFactory.getLogger(DeviceModelServiceImpl.class);

    private final DeviceModelRepository deviceModelRepository;

    private final DeviceModelMapper deviceModelMapper;

    public DeviceModelServiceImpl(DeviceModelRepository deviceModelRepository, DeviceModelMapper deviceModelMapper) {
        this.deviceModelRepository = deviceModelRepository;
        this.deviceModelMapper = deviceModelMapper;
    }

    @Override
    public DeviceModelDTO save(DeviceModelDTO deviceModelDTO) {
        log.debug("Request to save DeviceModel : {}", deviceModelDTO);
        DeviceModel deviceModel = deviceModelMapper.toEntity(deviceModelDTO);
        deviceModel = deviceModelRepository.save(deviceModel);
        return deviceModelMapper.toDto(deviceModel);
    }

    @Override
    public Optional<DeviceModelDTO> partialUpdate(DeviceModelDTO deviceModelDTO) {
        log.debug("Request to partially update DeviceModel : {}", deviceModelDTO);

        return deviceModelRepository
            .findById(deviceModelDTO.getId())
            .map(
                existingDeviceModel -> {
                    deviceModelMapper.partialUpdate(existingDeviceModel, deviceModelDTO);
                    return existingDeviceModel;
                }
            )
            .map(deviceModelRepository::save)
            .map(deviceModelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeviceModelDTO> findAll() {
        log.debug("Request to get all DeviceModels");
        return deviceModelRepository.findAll().stream().map(deviceModelMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DeviceModelDTO> findOne(Long id) {
        log.debug("Request to get DeviceModel : {}", id);
        return deviceModelRepository.findById(id).map(deviceModelMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DeviceModel : {}", id);
        deviceModelRepository.deleteById(id);
    }
}
