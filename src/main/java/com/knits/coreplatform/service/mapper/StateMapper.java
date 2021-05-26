package com.knits.coreplatform.service.mapper;

import com.knits.coreplatform.domain.*;
import com.knits.coreplatform.service.dto.StateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link State} and its DTO {@link StateDTO}.
 */
@Mapper(componentModel = "spring", uses = { ThingMapper.class })
public interface StateMapper extends EntityMapper<StateDTO, State> {
    @Mapping(target = "thing", source = "thing", qualifiedByName = "id")
    StateDTO toDto(State s);
}
