package com.knits.coreplatform.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.knits.coreplatform.domain.AlertMessage} entity.
 */
@ApiModel(description = "Entity for custom Alerts or Messages that device could define.\n@author Vassili Moskaljov\n@version 1.0")
public class AlertMessageDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private String dataType;

    private String value;

    private AlertConfigurationDTO alertConfiguration;

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

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public AlertConfigurationDTO getAlertConfiguration() {
        return alertConfiguration;
    }

    public void setAlertConfiguration(AlertConfigurationDTO alertConfiguration) {
        this.alertConfiguration = alertConfiguration;
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
        if (!(o instanceof AlertMessageDTO)) {
            return false;
        }

        AlertMessageDTO alertMessageDTO = (AlertMessageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, alertMessageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlertMessageDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", dataType='" + getDataType() + "'" +
            ", value='" + getValue() + "'" +
            ", alertConfiguration=" + getAlertConfiguration() +
            ", device=" + getDevice() +
            "}";
    }
}
