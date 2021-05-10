package com.knits.coreplatform.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.knits.coreplatform.domain.ConfigurationData} entity.
 */
@ApiModel(description = "Entity to add specific values to device configuration.\n@author Vassili Moskaljov\n@version 1.0")
public class ConfigurationDataDTO implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConfigurationDataDTO)) {
            return false;
        }

        ConfigurationDataDTO configurationDataDTO = (ConfigurationDataDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, configurationDataDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConfigurationDataDTO{" +
            "id=" + getId() +
            "}";
    }
}
