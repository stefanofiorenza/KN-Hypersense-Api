package com.knits.coreplatform.service.mapper;

import com.knits.coreplatform.domain.*;
import com.knits.coreplatform.service.dto.MetadataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Metadata} and its DTO {@link MetadataDTO}.
 */
@Mapper(componentModel = "spring", uses = { DeviceMapper.class })
public interface MetadataMapper extends EntityMapper<MetadataDTO, Metadata> {
    @Mapping(target = "device", source = "device", qualifiedByName = "id")
    MetadataDTO toDto(Metadata s);
}
