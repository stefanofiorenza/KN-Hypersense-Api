package com.knits.coreplatform.service.mapper;

import com.knits.coreplatform.domain.*;
import com.knits.coreplatform.service.dto.StatusDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Status} and its DTO {@link StatusDTO}.
 */
@Mapper(componentModel = "spring", uses = { DeviceMapper.class })
public interface StatusMapper extends EntityMapper<StatusDTO, Status> {
    @Mapping(target = "device", source = "device", qualifiedByName = "id")
    StatusDTO toDto(Status s);
}
