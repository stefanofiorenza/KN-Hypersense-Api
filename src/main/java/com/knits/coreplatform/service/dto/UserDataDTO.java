package com.knits.coreplatform.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.knits.coreplatform.domain.UserData} entity.
 */
@ApiModel(description = "Extended user data that is used instead of User entity.\n@author Vassili Moskaljov\n@version 1.0")
public class UserDataDTO implements Serializable {

    private Long id;

    private UserDTO internalUser;

    private OrganisationDTO organisation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getInternalUser() {
        return internalUser;
    }

    public void setInternalUser(UserDTO internalUser) {
        this.internalUser = internalUser;
    }

    public OrganisationDTO getOrganisation() {
        return organisation;
    }

    public void setOrganisation(OrganisationDTO organisation) {
        this.organisation = organisation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserDataDTO)) {
            return false;
        }

        UserDataDTO userDataDTO = (UserDataDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userDataDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserDataDTO{" +
            "id=" + getId() +
            ", internalUser=" + getInternalUser() +
            ", organisation=" + getOrganisation() +
            "}";
    }
}
