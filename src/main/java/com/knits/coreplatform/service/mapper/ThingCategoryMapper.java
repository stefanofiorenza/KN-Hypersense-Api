package com.knits.coreplatform.service.mapper;

import com.knits.coreplatform.domain.*;
import com.knits.coreplatform.service.dto.ThingCategoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ThingCategory} and its DTO {@link ThingCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ThingCategoryMapper extends EntityMapper<ThingCategoryDTO, ThingCategory> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ThingCategoryDTO toDtoId(ThingCategory thingCategory);
}
