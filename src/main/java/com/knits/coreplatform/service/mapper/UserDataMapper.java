package com.knits.coreplatform.service.mapper;

import com.knits.coreplatform.domain.*;
import com.knits.coreplatform.service.dto.UserDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserData} and its DTO {@link UserDataDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class, OrganisationMapper.class })
public interface UserDataMapper extends EntityMapper<UserDataDTO, UserData> {
    @Mapping(target = "internalUser", source = "internalUser", qualifiedByName = "id")
    @Mapping(target = "organisation", source = "organisation", qualifiedByName = "id")
    UserDataDTO toDto(UserData s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDataDTO toDtoId(UserData userData);
}
