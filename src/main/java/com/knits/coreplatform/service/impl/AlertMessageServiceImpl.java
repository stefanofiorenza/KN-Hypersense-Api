package com.knits.coreplatform.service.impl;

import com.knits.coreplatform.domain.AlertMessage;
import com.knits.coreplatform.repository.AlertMessageRepository;
import com.knits.coreplatform.service.AlertMessageService;
import com.knits.coreplatform.service.dto.AlertMessageDTO;
import com.knits.coreplatform.service.mapper.AlertMessageMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AlertMessage}.
 */
@Service
@Transactional
public class AlertMessageServiceImpl implements AlertMessageService {

    private final Logger log = LoggerFactory.getLogger(AlertMessageServiceImpl.class);

    private final AlertMessageRepository alertMessageRepository;

    private final AlertMessageMapper alertMessageMapper;

    public AlertMessageServiceImpl(AlertMessageRepository alertMessageRepository, AlertMessageMapper alertMessageMapper) {
        this.alertMessageRepository = alertMessageRepository;
        this.alertMessageMapper = alertMessageMapper;
    }

    @Override
    public AlertMessageDTO save(AlertMessageDTO alertMessageDTO) {
        log.debug("Request to save AlertMessage : {}", alertMessageDTO);
        AlertMessage alertMessage = alertMessageMapper.toEntity(alertMessageDTO);
        alertMessage = alertMessageRepository.save(alertMessage);
        return alertMessageMapper.toDto(alertMessage);
    }

    @Override
    public Optional<AlertMessageDTO> partialUpdate(AlertMessageDTO alertMessageDTO) {
        log.debug("Request to partially update AlertMessage : {}", alertMessageDTO);

        return alertMessageRepository
            .findById(alertMessageDTO.getId())
            .map(
                existingAlertMessage -> {
                    alertMessageMapper.partialUpdate(existingAlertMessage, alertMessageDTO);
                    return existingAlertMessage;
                }
            )
            .map(alertMessageRepository::save)
            .map(alertMessageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlertMessageDTO> findAll() {
        log.debug("Request to get all AlertMessages");
        return alertMessageRepository.findAll().stream().map(alertMessageMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AlertMessageDTO> findOne(Long id) {
        log.debug("Request to get AlertMessage : {}", id);
        return alertMessageRepository.findById(id).map(alertMessageMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AlertMessage : {}", id);
        alertMessageRepository.deleteById(id);
    }
}
