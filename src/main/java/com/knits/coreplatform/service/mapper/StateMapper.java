package com.knits.coreplatform.service.mapper;

import com.knits.coreplatform.domain.*;
import com.knits.coreplatform.service.dto.StateDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link State} and its DTO {@link StateDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StateMapper extends EntityMapper<StateDTO, State> {
    @Named("nameSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    Set<StateDTO> toDtoNameSet(Set<State> state);
}
