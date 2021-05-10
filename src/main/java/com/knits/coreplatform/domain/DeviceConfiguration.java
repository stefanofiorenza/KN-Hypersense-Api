package com.knits.coreplatform.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entity to keep data related to sensor connection.\n@author Vassili Moskaljov\n@version 1.0
 */
@Entity
@Table(name = "device_configuration")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeviceConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "u_uid")
    private String uUID;

    @Lob
    @Column(name = "token")
    private byte[] token;

    @Column(name = "token_content_type")
    private String tokenContentType;

    @OneToOne
    @JoinColumn(unique = true)
    private ConfigurationData configurationData;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "telemetry",
            "supplier",
            "deviceModel",
            "rules",
            "alertMessages",
            "metaData",
            "deviceConfigurations",
            "statuses",
            "thing",
            "deviceGroup",
        },
        allowSetters = true
    )
    private Device device;

    @ManyToOne
    @JsonIgnoreProperties(value = { "internalUser", "deviceConfigurations", "organisation" }, allowSetters = true)
    private UserData userData;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DeviceConfiguration id(Long id) {
        this.id = id;
        return this;
    }

    public String getuUID() {
        return this.uUID;
    }

    public DeviceConfiguration uUID(String uUID) {
        this.uUID = uUID;
        return this;
    }

    public void setuUID(String uUID) {
        this.uUID = uUID;
    }

    public byte[] getToken() {
        return this.token;
    }

    public DeviceConfiguration token(byte[] token) {
        this.token = token;
        return this;
    }

    public void setToken(byte[] token) {
        this.token = token;
    }

    public String getTokenContentType() {
        return this.tokenContentType;
    }

    public DeviceConfiguration tokenContentType(String tokenContentType) {
        this.tokenContentType = tokenContentType;
        return this;
    }

    public void setTokenContentType(String tokenContentType) {
        this.tokenContentType = tokenContentType;
    }

    public ConfigurationData getConfigurationData() {
        return this.configurationData;
    }

    public DeviceConfiguration configurationData(ConfigurationData configurationData) {
        this.setConfigurationData(configurationData);
        return this;
    }

    public void setConfigurationData(ConfigurationData configurationData) {
        this.configurationData = configurationData;
    }

    public Device getDevice() {
        return this.device;
    }

    public DeviceConfiguration device(Device device) {
        this.setDevice(device);
        return this;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public UserData getUserData() {
        return this.userData;
    }

    public DeviceConfiguration userData(UserData userData) {
        this.setUserData(userData);
        return this;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceConfiguration)) {
            return false;
        }
        return id != null && id.equals(((DeviceConfiguration) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeviceConfiguration{" +
            "id=" + getId() +
            ", uUID='" + getuUID() + "'" +
            ", token='" + getToken() + "'" +
            ", tokenContentType='" + getTokenContentType() + "'" +
            "}";
    }
}
