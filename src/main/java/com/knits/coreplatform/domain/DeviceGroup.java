package com.knits.coreplatform.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entity to Group up sertain devices.\n@author Vassili Moskaljov\n@version 1.0
 */
@Entity
@Table(name = "device_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeviceGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "deviceGroup")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "telemetry",
            "supplier",
            "deviceModel",
            "rules",
            "alertMessages",
            "metaData",
            "deviceConfigurations",
            "thing",
            "deviceGroup",
            "status",
        },
        allowSetters = true
    )
    private Set<Device> devices = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DeviceGroup id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public DeviceGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public DeviceGroup description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Device> getDevices() {
        return this.devices;
    }

    public DeviceGroup devices(Set<Device> devices) {
        this.setDevices(devices);
        return this;
    }

    public DeviceGroup addDevice(Device device) {
        this.devices.add(device);
        device.setDeviceGroup(this);
        return this;
    }

    public DeviceGroup removeDevice(Device device) {
        this.devices.remove(device);
        device.setDeviceGroup(null);
        return this;
    }

    public void setDevices(Set<Device> devices) {
        if (this.devices != null) {
            this.devices.forEach(i -> i.setDeviceGroup(null));
        }
        if (devices != null) {
            devices.forEach(i -> i.setDeviceGroup(this));
        }
        this.devices = devices;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceGroup)) {
            return false;
        }
        return id != null && id.equals(((DeviceGroup) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeviceGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
