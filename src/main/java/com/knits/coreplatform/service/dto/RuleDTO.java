package com.knits.coreplatform.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.knits.coreplatform.domain.Rule} entity.
 */
@ApiModel(
    description = "Entity for Optional information for custom Action that sensors can define.\n@author Vassili Moskaljov\n@version 1.0"
)
public class RuleDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private String alias;

    private DeviceDTO device;

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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public DeviceDTO getDevice() {
        return device;
    }

    public void setDevice(DeviceDTO device) {
        this.device = device;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RuleDTO)) {
            return false;
        }

        RuleDTO ruleDTO = (RuleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ruleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RuleDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", alias='" + getAlias() + "'" +
            ", device=" + getDevice() +
            "}";
    }
}
