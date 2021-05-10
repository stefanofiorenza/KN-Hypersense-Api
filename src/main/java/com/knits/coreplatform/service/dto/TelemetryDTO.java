package com.knits.coreplatform.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.knits.coreplatform.domain.Telemetry} entity.
 */
@ApiModel(description = "Entity for data that being transfered from devices.\n@author Vassili Moskaljov\n@version 1.0")
public class TelemetryDTO implements Serializable {

    private Long id;

    private String name;

    private String data;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TelemetryDTO)) {
            return false;
        }

        TelemetryDTO telemetryDTO = (TelemetryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, telemetryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TelemetryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", data='" + getData() + "'" +
            "}";
    }
}
