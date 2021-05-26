package com.knits.coreplatform.service.mapper;

import com.knits.coreplatform.domain.*;
import com.knits.coreplatform.service.dto.DeviceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Device} and its DTO {@link DeviceDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = {
        TelemetryMapper.class,
        DeviceConfigurationMapper.class,
        SupplierMapper.class,
        DeviceModelMapper.class,
        ThingMapper.class,
        DeviceGroupMapper.class,
    }
)
public interface DeviceMapper extends EntityMapper<DeviceDTO, Device> {
    @Mapping(target = "telemetry", source = "telemetry", qualifiedByName = "id")
    @Mapping(target = "deviceConfiguration", source = "deviceConfiguration", qualifiedByName = "id")
    @Mapping(target = "supplier", source = "supplier", qualifiedByName = "id")
    @Mapping(target = "deviceModel", source = "deviceModel", qualifiedByName = "id")
    @Mapping(target = "thing", source = "thing", qualifiedByName = "id")
    @Mapping(target = "deviceGroup", source = "deviceGroup", qualifiedByName = "id")
    DeviceDTO toDto(Device s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DeviceDTO toDtoId(Device device);
}
