package com.knits.coreplatform.service.mapper;

import com.knits.coreplatform.domain.*;
import com.knits.coreplatform.service.dto.ConfigurationDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ConfigurationData} and its DTO {@link ConfigurationDataDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConfigurationDataMapper extends EntityMapper<ConfigurationDataDTO, ConfigurationData> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ConfigurationDataDTO toDtoId(ConfigurationData configurationData);
}
