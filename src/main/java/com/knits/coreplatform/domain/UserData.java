package com.knits.coreplatform.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Extended user data that is used instead of User entity.\n@author Vassili Moskaljov\n@version 1.0
 */
@Entity
@Table(name = "user_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @OneToOne
    @JoinColumn(unique = true)
    private User internalUser;

    @OneToMany(mappedBy = "userData")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "configurationData", "userData" }, allowSetters = true)
    private Set<DeviceConfiguration> deviceConfigurations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "factories", "userData" }, allowSetters = true)
    private Organisation organisation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserData id(Long id) {
        this.id = id;
        return this;
    }

    public String getUuid() {
        return this.uuid;
    }

    public UserData uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public User getInternalUser() {
        return this.internalUser;
    }

    public UserData internalUser(User user) {
        this.setInternalUser(user);
        return this;
    }

    public void setInternalUser(User user) {
        this.internalUser = user;
    }

    public Set<DeviceConfiguration> getDeviceConfigurations() {
        return this.deviceConfigurations;
    }

    public UserData deviceConfigurations(Set<DeviceConfiguration> deviceConfigurations) {
        this.setDeviceConfigurations(deviceConfigurations);
        return this;
    }

    public UserData addDeviceConfiguration(DeviceConfiguration deviceConfiguration) {
        this.deviceConfigurations.add(deviceConfiguration);
        deviceConfiguration.setUserData(this);
        return this;
    }

    public UserData removeDeviceConfiguration(DeviceConfiguration deviceConfiguration) {
        this.deviceConfigurations.remove(deviceConfiguration);
        deviceConfiguration.setUserData(null);
        return this;
    }

    public void setDeviceConfigurations(Set<DeviceConfiguration> deviceConfigurations) {
        if (this.deviceConfigurations != null) {
            this.deviceConfigurations.forEach(i -> i.setUserData(null));
        }
        if (deviceConfigurations != null) {
            deviceConfigurations.forEach(i -> i.setUserData(this));
        }
        this.deviceConfigurations = deviceConfigurations;
    }

    public Organisation getOrganisation() {
        return this.organisation;
    }

    public UserData organisation(Organisation organisation) {
        this.setOrganisation(organisation);
        return this;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserData)) {
            return false;
        }
        return id != null && id.equals(((UserData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserData{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            "}";
    }
}
