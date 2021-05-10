package com.knits.coreplatform.service.mapper;

import com.knits.coreplatform.domain.*;
import com.knits.coreplatform.service.dto.DeviceGroupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DeviceGroup} and its DTO {@link DeviceGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DeviceGroupMapper extends EntityMapper<DeviceGroupDTO, DeviceGroup> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DeviceGroupDTO toDtoId(DeviceGroup deviceGroup);
}
