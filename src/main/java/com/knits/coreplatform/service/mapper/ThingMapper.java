package com.knits.coreplatform.service.mapper;

import com.knits.coreplatform.domain.*;
import com.knits.coreplatform.service.dto.ThingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Thing} and its DTO {@link ThingDTO}.
 */
@Mapper(componentModel = "spring", uses = { LocationMapper.class, ThingCategoryMapper.class, ApplicationMapper.class, StateMapper.class })
public interface ThingMapper extends EntityMapper<ThingDTO, Thing> {
    @Mapping(target = "location", source = "location", qualifiedByName = "id")
    @Mapping(target = "thingCategory", source = "thingCategory", qualifiedByName = "id")
    @Mapping(target = "application", source = "application", qualifiedByName = "id")
    @Mapping(target = "states", source = "states", qualifiedByName = "nameSet")
    ThingDTO toDto(Thing s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ThingDTO toDtoId(Thing thing);

    @Mapping(target = "removeState", ignore = true)
    Thing toEntity(ThingDTO thingDTO);
}
