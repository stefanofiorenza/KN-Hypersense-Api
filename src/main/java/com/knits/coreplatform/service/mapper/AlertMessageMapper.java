package com.knits.coreplatform.service.mapper;

import com.knits.coreplatform.domain.*;
import com.knits.coreplatform.service.dto.AlertMessageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AlertMessage} and its DTO {@link AlertMessageDTO}.
 */
@Mapper(componentModel = "spring", uses = { AlertConfigurationMapper.class, DeviceMapper.class })
public interface AlertMessageMapper extends EntityMapper<AlertMessageDTO, AlertMessage> {
    @Mapping(target = "alertConfiguration", source = "alertConfiguration", qualifiedByName = "id")
    @Mapping(target = "device", source = "device", qualifiedByName = "id")
    AlertMessageDTO toDto(AlertMessage s);
}
