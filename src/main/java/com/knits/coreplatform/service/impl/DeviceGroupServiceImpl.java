package com.knits.coreplatform.service.impl;

import com.knits.coreplatform.domain.DeviceGroup;
import com.knits.coreplatform.repository.DeviceGroupRepository;
import com.knits.coreplatform.service.DeviceGroupService;
import com.knits.coreplatform.service.dto.DeviceGroupDTO;
import com.knits.coreplatform.service.mapper.DeviceGroupMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DeviceGroup}.
 */
@Service
@Transactional
public class DeviceGroupServiceImpl implements DeviceGroupService {

    private final Logger log = LoggerFactory.getLogger(DeviceGroupServiceImpl.class);

    private final DeviceGroupRepository deviceGroupRepository;

    private final DeviceGroupMapper deviceGroupMapper;

    public DeviceGroupServiceImpl(DeviceGroupRepository deviceGroupRepository, DeviceGroupMapper deviceGroupMapper) {
        this.deviceGroupRepository = deviceGroupRepository;
        this.deviceGroupMapper = deviceGroupMapper;
    }

    @Override
    public DeviceGroupDTO save(DeviceGroupDTO deviceGroupDTO) {
        log.debug("Request to save DeviceGroup : {}", deviceGroupDTO);
        DeviceGroup deviceGroup = deviceGroupMapper.toEntity(deviceGroupDTO);
        deviceGroup = deviceGroupRepository.save(deviceGroup);
        return deviceGroupMapper.toDto(deviceGroup);
    }

    @Override
    public Optional<DeviceGroupDTO> partialUpdate(DeviceGroupDTO deviceGroupDTO) {
        log.debug("Request to partially update DeviceGroup : {}", deviceGroupDTO);

        return deviceGroupRepository
            .findById(deviceGroupDTO.getId())
            .map(
                existingDeviceGroup -> {
                    deviceGroupMapper.partialUpdate(existingDeviceGroup, deviceGroupDTO);
                    return existingDeviceGroup;
                }
            )
            .map(deviceGroupRepository::save)
            .map(deviceGroupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeviceGroupDTO> findAll() {
        log.debug("Request to get all DeviceGroups");
        return deviceGroupRepository.findAll().stream().map(deviceGroupMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DeviceGroupDTO> findOne(Long id) {
        log.debug("Request to get DeviceGroup : {}", id);
        return deviceGroupRepository.findById(id).map(deviceGroupMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DeviceGroup : {}", id);
        deviceGroupRepository.deleteById(id);
    }
}
