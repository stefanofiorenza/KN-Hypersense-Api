package com.knits.coreplatform.service.mapper;

import com.knits.coreplatform.domain.*;
import com.knits.coreplatform.service.dto.RuleConfigurationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RuleConfiguration} and its DTO {@link RuleConfigurationDTO}.
 */
@Mapper(componentModel = "spring", uses = { RuleMapper.class })
public interface RuleConfigurationMapper extends EntityMapper<RuleConfigurationDTO, RuleConfiguration> {
    @Mapping(target = "rule", source = "rule", qualifiedByName = "id")
    RuleConfigurationDTO toDto(RuleConfiguration s);
}
