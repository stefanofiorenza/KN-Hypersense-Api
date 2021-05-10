package com.knits.coreplatform.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.knits.coreplatform.domain.Metadata} entity.
 */
@ApiModel(description = "Entity for metadata that sensor could contain.\n@author Vassili Moskaljov\n@version 1.0")
public class MetadataDTO implements Serializable {

    private Long id;

    private String name;

    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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
        if (!(o instanceof MetadataDTO)) {
            return false;
        }

        MetadataDTO metadataDTO = (MetadataDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, metadataDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MetadataDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", data='" + getData() + "'" +
            ", device=" + getDevice() +
            "}";
    }
}
