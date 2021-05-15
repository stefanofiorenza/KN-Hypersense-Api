package com.knits.coreplatform.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Application entity, specific application to use core platform.\n@author Vassili Moskaljov\n@version 1.0
 */
@Entity
@Table(name = "application")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "is_authorized")
    private Boolean isAuthorized;

    @OneToMany(mappedBy = "application")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "location", "devices", "thingCategory", "application", "states" }, allowSetters = true)
    private Set<Thing> things = new HashSet<>();

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

    public Application id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Application name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Application description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsAuthorized() {
        return this.isAuthorized;
    }

    public Application isAuthorized(Boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
        return this;
    }

    public void setIsAuthorized(Boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
    }

    public Set<Thing> getThings() {
        return this.things;
    }

    public Application things(Set<Thing> things) {
        this.setThings(things);
        return this;
    }

    public Application addThing(Thing thing) {
        this.things.add(thing);
        thing.setApplication(this);
        return this;
    }

    public Application removeThing(Thing thing) {
        this.things.remove(thing);
        thing.setApplication(null);
        return this;
    }

    public void setThings(Set<Thing> things) {
        if (this.things != null) {
            this.things.forEach(i -> i.setApplication(null));
        }
        if (things != null) {
            things.forEach(i -> i.setApplication(this));
        }
        this.things = things;
    }

    public Organisation getOrganisation() {
        return this.organisation;
    }

    public Application organisation(Organisation organisation) {
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
        if (!(o instanceof Application)) {
            return false;
        }
        return id != null && id.equals(((Application) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Application{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", isAuthorized='" + getIsAuthorized() + "'" +
            "}";
    }
}
