package com.knits.coreplatform.service.mapper;

import com.knits.coreplatform.domain.*;
import com.knits.coreplatform.service.dto.RuleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rule} and its DTO {@link RuleDTO}.
 */
@Mapper(componentModel = "spring", uses = { DeviceMapper.class })
public interface RuleMapper extends EntityMapper<RuleDTO, Rule> {
    @Mapping(target = "device", source = "device", qualifiedByName = "id")
    RuleDTO toDto(Rule s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RuleDTO toDtoId(Rule rule);
}
