package com.knits.coreplatform.service.mapper;

import com.knits.coreplatform.domain.*;
import com.knits.coreplatform.service.dto.DeviceConfigurationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DeviceConfiguration} and its DTO {@link DeviceConfigurationDTO}.
 */
@Mapper(componentModel = "spring", uses = { ConfigurationDataMapper.class, UserDataMapper.class })
public interface DeviceConfigurationMapper extends EntityMapper<DeviceConfigurationDTO, DeviceConfiguration> {
    @Mapping(target = "configurationData", source = "configurationData", qualifiedByName = "id")
    @Mapping(target = "userData", source = "userData", qualifiedByName = "id")
    DeviceConfigurationDTO toDto(DeviceConfiguration s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DeviceConfigurationDTO toDtoId(DeviceConfiguration deviceConfiguration);
}
