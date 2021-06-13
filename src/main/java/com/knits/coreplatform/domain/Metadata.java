package com.knits.coreplatform.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entity for metadata that sensor could contain.\n@author Vassili Moskaljov\n@version 1.0
 */
@Entity
@Table(name = "metadata")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Metadata implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "data")
    private String data;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonIgnoreProperties(
        value = {
            "telemetry",
            "deviceConfiguration",
            "supplier",
            "deviceModel",
            "rules",
            "alertMessages",
            "metaData",
            "statuses",
            "thing",
            "deviceGroup",
        },
        allowSetters = true
    )
    private Device device;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Metadata id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Metadata name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return this.data;
    }

    public Metadata data(String data) {
        this.data = data;
        return this;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Device getDevice() {
        return this.device;
    }

    public Metadata device(Device device) {
        this.setDevice(device);
        return this;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Metadata)) {
            return false;
        }
        return id != null && id.equals(((Metadata) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Metadata{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", data='" + getData() + "'" +
            "}";
    }
}
