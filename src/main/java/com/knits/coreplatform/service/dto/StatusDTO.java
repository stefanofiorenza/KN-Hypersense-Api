package com.knits.coreplatform.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.knits.coreplatform.domain.Status} entity.
 */
@ApiModel(description = "Entity to define possible Device status.\n@author Vassili Moskaljov\n@version 1.0")
public class StatusDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private String uUID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getuUID() {
        return uUID;
    }

    public void setuUID(String uUID) {
        this.uUID = uUID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StatusDTO)) {
            return false;
        }

        StatusDTO statusDTO = (StatusDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, statusDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatusDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", uUID='" + getuUID() + "'" +
            "}";
    }
}
