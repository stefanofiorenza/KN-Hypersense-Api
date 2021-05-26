package com.knits.coreplatform.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.knits.coreplatform.domain.DeviceModel} entity.
 */
@ApiModel(description = "Entity to set device model specification.\n@author Vassili Moskaljov\n@version 1.0")
public class DeviceModelDTO implements Serializable {

    private Long id;

    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceModelDTO)) {
            return false;
        }

        DeviceModelDTO deviceModelDTO = (DeviceModelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, deviceModelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeviceModelDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
