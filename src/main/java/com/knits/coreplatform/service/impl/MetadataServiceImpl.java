package com.knits.coreplatform.service.impl;

import com.knits.coreplatform.domain.Metadata;
import com.knits.coreplatform.repository.MetadataRepository;
import com.knits.coreplatform.service.MetadataService;
import com.knits.coreplatform.service.dto.MetadataDTO;
import com.knits.coreplatform.service.mapper.MetadataMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Metadata}.
 */
@Service
@Transactional
public class MetadataServiceImpl implements MetadataService {

    private final Logger log = LoggerFactory.getLogger(MetadataServiceImpl.class);

    private final MetadataRepository metadataRepository;

    private final MetadataMapper metadataMapper;

    public MetadataServiceImpl(MetadataRepository metadataRepository, MetadataMapper metadataMapper) {
        this.metadataRepository = metadataRepository;
        this.metadataMapper = metadataMapper;
    }

    @Override
    public MetadataDTO save(MetadataDTO metadataDTO) {
        log.debug("Request to save Metadata : {}", metadataDTO);
        Metadata metadata = metadataMapper.toEntity(metadataDTO);
        metadata = metadataRepository.save(metadata);
        return metadataMapper.toDto(metadata);
    }

    @Override
    public Optional<MetadataDTO> partialUpdate(MetadataDTO metadataDTO) {
        log.debug("Request to partially update Metadata : {}", metadataDTO);

        return metadataRepository
            .findById(metadataDTO.getId())
            .map(
                existingMetadata -> {
                    metadataMapper.partialUpdate(existingMetadata, metadataDTO);
                    return existingMetadata;
                }
            )
            .map(metadataRepository::save)
            .map(metadataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MetadataDTO> findAll() {
        log.debug("Request to get all Metadata");
        return metadataRepository.findAll().stream().map(metadataMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MetadataDTO> findOne(Long id) {
        log.debug("Request to get Metadata : {}", id);
        return metadataRepository.findById(id).map(metadataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Metadata : {}", id);
        metadataRepository.deleteById(id);
    }
}
