package com.knits.coreplatform.service.impl;

import com.knits.coreplatform.domain.Telemetry;
import com.knits.coreplatform.repository.TelemetryRepository;
import com.knits.coreplatform.service.TelemetryService;
import com.knits.coreplatform.service.dto.TelemetryDTO;
import com.knits.coreplatform.service.mapper.TelemetryMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Telemetry}.
 */
@Service
@Transactional
public class TelemetryServiceImpl implements TelemetryService {

    private final Logger log = LoggerFactory.getLogger(TelemetryServiceImpl.class);

    private final TelemetryRepository telemetryRepository;

    private final TelemetryMapper telemetryMapper;

    public TelemetryServiceImpl(TelemetryRepository telemetryRepository, TelemetryMapper telemetryMapper) {
        this.telemetryRepository = telemetryRepository;
        this.telemetryMapper = telemetryMapper;
    }

    @Override
    public TelemetryDTO save(TelemetryDTO telemetryDTO) {
        log.debug("Request to save Telemetry : {}", telemetryDTO);
        Telemetry telemetry = telemetryMapper.toEntity(telemetryDTO);
        telemetry = telemetryRepository.save(telemetry);
        return telemetryMapper.toDto(telemetry);
    }

    @Override
    public Optional<TelemetryDTO> partialUpdate(TelemetryDTO telemetryDTO) {
        log.debug("Request to partially update Telemetry : {}", telemetryDTO);

        return telemetryRepository
            .findById(telemetryDTO.getId())
            .map(
                existingTelemetry -> {
                    telemetryMapper.partialUpdate(existingTelemetry, telemetryDTO);
                    return existingTelemetry;
                }
            )
            .map(telemetryRepository::save)
            .map(telemetryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TelemetryDTO> findAll() {
        log.debug("Request to get all Telemetries");
        return telemetryRepository.findAll().stream().map(telemetryMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TelemetryDTO> findOne(Long id) {
        log.debug("Request to get Telemetry : {}", id);
        return telemetryRepository.findById(id).map(telemetryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Telemetry : {}", id);
        telemetryRepository.deleteById(id);
    }
}
