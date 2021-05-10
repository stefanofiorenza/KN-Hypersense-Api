package com.knits.coreplatform.service.impl;

import com.knits.coreplatform.domain.ThingCategory;
import com.knits.coreplatform.repository.ThingCategoryRepository;
import com.knits.coreplatform.service.ThingCategoryService;
import com.knits.coreplatform.service.dto.ThingCategoryDTO;
import com.knits.coreplatform.service.mapper.ThingCategoryMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ThingCategory}.
 */
@Service
@Transactional
public class ThingCategoryServiceImpl implements ThingCategoryService {

    private final Logger log = LoggerFactory.getLogger(ThingCategoryServiceImpl.class);

    private final ThingCategoryRepository thingCategoryRepository;

    private final ThingCategoryMapper thingCategoryMapper;

    public ThingCategoryServiceImpl(ThingCategoryRepository thingCategoryRepository, ThingCategoryMapper thingCategoryMapper) {
        this.thingCategoryRepository = thingCategoryRepository;
        this.thingCategoryMapper = thingCategoryMapper;
    }

    @Override
    public ThingCategoryDTO save(ThingCategoryDTO thingCategoryDTO) {
        log.debug("Request to save ThingCategory : {}", thingCategoryDTO);
        ThingCategory thingCategory = thingCategoryMapper.toEntity(thingCategoryDTO);
        thingCategory = thingCategoryRepository.save(thingCategory);
        return thingCategoryMapper.toDto(thingCategory);
    }

    @Override
    public Optional<ThingCategoryDTO> partialUpdate(ThingCategoryDTO thingCategoryDTO) {
        log.debug("Request to partially update ThingCategory : {}", thingCategoryDTO);

        return thingCategoryRepository
            .findById(thingCategoryDTO.getId())
            .map(
                existingThingCategory -> {
                    thingCategoryMapper.partialUpdate(existingThingCategory, thingCategoryDTO);
                    return existingThingCategory;
                }
            )
            .map(thingCategoryRepository::save)
            .map(thingCategoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ThingCategoryDTO> findAll() {
        log.debug("Request to get all ThingCategories");
        return thingCategoryRepository.findAll().stream().map(thingCategoryMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ThingCategoryDTO> findOne(Long id) {
        log.debug("Request to get ThingCategory : {}", id);
        return thingCategoryRepository.findById(id).map(thingCategoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ThingCategory : {}", id);
        thingCategoryRepository.deleteById(id);
    }
}
