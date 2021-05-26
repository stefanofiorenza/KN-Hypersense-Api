package com.knits.coreplatform.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.knits.coreplatform.domain.Device} entity.
 */
@ApiModel(description = "Entity that holds up Sensor data.\n@author Vassili Moskaljov\n@version 1.0")
public class DeviceDTO implements Serializable {

    private Long id;

    private String name;

    private String serialNumber;

    private String manufacturer;

    private TelemetryDTO telemetry;

    private DeviceConfigurationDTO deviceConfiguration;

    private SupplierDTO supplier;

    private DeviceModelDTO deviceModel;

    private ThingDTO thing;

    private DeviceGroupDTO deviceGroup;

    private StatusDTO status;

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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public TelemetryDTO getTelemetry() {
        return telemetry;
    }

    public void setTelemetry(TelemetryDTO telemetry) {
        this.telemetry = telemetry;
    }

    public DeviceConfigurationDTO getDeviceConfiguration() {
        return deviceConfiguration;
    }

    public void setDeviceConfiguration(DeviceConfigurationDTO deviceConfiguration) {
        this.deviceConfiguration = deviceConfiguration;
    }

    public SupplierDTO getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierDTO supplier) {
        this.supplier = supplier;
    }

    public DeviceModelDTO getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(DeviceModelDTO deviceModel) {
        this.deviceModel = deviceModel;
    }

    public ThingDTO getThing() {
        return thing;
    }

    public void setThing(ThingDTO thing) {
        this.thing = thing;
    }

    public DeviceGroupDTO getDeviceGroup() {
        return deviceGroup;
    }

    public void setDeviceGroup(DeviceGroupDTO deviceGroup) {
        this.deviceGroup = deviceGroup;
    }

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceDTO)) {
            return false;
        }

        DeviceDTO deviceDTO = (DeviceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, deviceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeviceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", serialNumber='" + getSerialNumber() + "'" +
            ", manufacturer='" + getManufacturer() + "'" +
            ", telemetry=" + getTelemetry() +
            ", deviceConfiguration=" + getDeviceConfiguration() +
            ", supplier=" + getSupplier() +
            ", deviceModel=" + getDeviceModel() +
            ", thing=" + getThing() +
            ", deviceGroup=" + getDeviceGroup() +
            ", status=" + getStatus() +
            "}";
    }
}
