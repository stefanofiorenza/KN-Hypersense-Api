package com.knits.coreplatform.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entity for custom Alerts or Messages that device could define.\n@author Vassili Moskaljov\n@version 1.0
 */
@Entity
@Table(name = "alert_message")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AlertMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "data_type")
    private String dataType;

    @Column(name = "value")
    private String value;

    @OneToOne
    @JoinColumn(unique = true)
    private AlertConfiguration alertConfiguration;

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

    public AlertMessage id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public AlertMessage name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public AlertMessage description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDataType() {
        return this.dataType;
    }

    public AlertMessage dataType(String dataType) {
        this.dataType = dataType;
        return this;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getValue() {
        return this.value;
    }

    public AlertMessage value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public AlertConfiguration getAlertConfiguration() {
        return this.alertConfiguration;
    }

    public AlertMessage alertConfiguration(AlertConfiguration alertConfiguration) {
        this.setAlertConfiguration(alertConfiguration);
        return this;
    }

    public void setAlertConfiguration(AlertConfiguration alertConfiguration) {
        this.alertConfiguration = alertConfiguration;
    }

    public Device getDevice() {
        return this.device;
    }

    public AlertMessage device(Device device) {
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
        if (!(o instanceof AlertMessage)) {
            return false;
        }
        return id != null && id.equals(((AlertMessage) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlertMessage{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", dataType='" + getDataType() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
