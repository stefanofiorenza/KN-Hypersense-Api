package com.knits.coreplatform.service.mapper;

import com.knits.coreplatform.domain.*;
import com.knits.coreplatform.service.dto.AlertConfigurationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AlertConfiguration} and its DTO {@link AlertConfigurationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AlertConfigurationMapper extends EntityMapper<AlertConfigurationDTO, AlertConfiguration> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AlertConfigurationDTO toDtoId(AlertConfiguration alertConfiguration);
}
