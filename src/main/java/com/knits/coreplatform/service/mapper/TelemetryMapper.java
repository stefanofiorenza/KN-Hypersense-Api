package com.knits.coreplatform.service.mapper;

import com.knits.coreplatform.domain.*;
import com.knits.coreplatform.service.dto.TelemetryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Telemetry} and its DTO {@link TelemetryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TelemetryMapper extends EntityMapper<TelemetryDTO, Telemetry> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TelemetryDTO toDtoId(Telemetry telemetry);
}
