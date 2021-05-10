package com.knits.coreplatform.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Organisation entity that is related to user\n@author Vassili Moskaljov\n@version 1.0
 */
@Entity
@Table(name = "organisation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Organisation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "organisation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "things", "organisation" }, allowSetters = true)
    private Set<Application> factories = new HashSet<>();

    @OneToMany(mappedBy = "organisation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "internalUser", "deviceConfigurations", "organisation" }, allowSetters = true)
    private Set<UserData> userData = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Organisation id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Organisation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Application> getFactories() {
        return this.factories;
    }

    public Organisation factories(Set<Application> applications) {
        this.setFactories(applications);
        return this;
    }

    public Organisation addFactory(Application application) {
        this.factories.add(application);
        application.setOrganisation(this);
        return this;
    }

    public Organisation removeFactory(Application application) {
        this.factories.remove(application);
        application.setOrganisation(null);
        return this;
    }

    public void setFactories(Set<Application> applications) {
        if (this.factories != null) {
            this.factories.forEach(i -> i.setOrganisation(null));
        }
        if (applications != null) {
            applications.forEach(i -> i.setOrganisation(this));
        }
        this.factories = applications;
    }

    public Set<UserData> getUserData() {
        return this.userData;
    }

    public Organisation userData(Set<UserData> userData) {
        this.setUserData(userData);
        return this;
    }

    public Organisation addUserData(UserData userData) {
        this.userData.add(userData);
        userData.setOrganisation(this);
        return this;
    }

    public Organisation removeUserData(UserData userData) {
        this.userData.remove(userData);
        userData.setOrganisation(null);
        return this;
    }

    public void setUserData(Set<UserData> userData) {
        if (this.userData != null) {
            this.userData.forEach(i -> i.setOrganisation(null));
        }
        if (userData != null) {
            userData.forEach(i -> i.setOrganisation(this));
        }
        this.userData = userData;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Organisation)) {
            return false;
        }
        return id != null && id.equals(((Organisation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Organisation{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
