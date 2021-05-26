package com.knits.coreplatform.service.impl;

import com.knits.coreplatform.domain.Thing;
import com.knits.coreplatform.repository.ThingRepository;
import com.knits.coreplatform.service.ThingService;
import com.knits.coreplatform.service.dto.ThingDTO;
import com.knits.coreplatform.service.mapper.ThingMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Thing}.
 */
@Service
@Transactional
public class ThingServiceImpl implements ThingService {

    private final Logger log = LoggerFactory.getLogger(ThingServiceImpl.class);

    private final ThingRepository thingRepository;

    private final ThingMapper thingMapper;

    public ThingServiceImpl(ThingRepository thingRepository, ThingMapper thingMapper) {
        this.thingRepository = thingRepository;
        this.thingMapper = thingMapper;
    }

    @Override
    public ThingDTO save(ThingDTO thingDTO) {
        log.debug("Request to save Thing : {}", thingDTO);
        Thing thing = thingMapper.toEntity(thingDTO);
        thing = thingRepository.save(thing);
        return thingMapper.toDto(thing);
    }

    @Override
    public Optional<ThingDTO> partialUpdate(ThingDTO thingDTO) {
        log.debug("Request to partially update Thing : {}", thingDTO);

        return thingRepository
            .findById(thingDTO.getId())
            .map(
                existingThing -> {
                    thingMapper.partialUpdate(existingThing, thingDTO);
                    return existingThing;
                }
            )
            .map(thingRepository::save)
            .map(thingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ThingDTO> findAll() {
        log.debug("Request to get all Things");
        return thingRepository.findAll().stream().map(thingMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ThingDTO> findOne(Long id) {
        log.debug("Request to get Thing : {}", id);
        return thingRepository.findById(id).map(thingMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Thing : {}", id);
        thingRepository.deleteById(id);
    }
}
