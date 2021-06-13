package com.knits.coreplatform.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Thing is a representation of place to attach sensorts.\n@author Vassili Moskaljov\n@version 1.0
 */
@Entity
@Table(name = "thing")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Thing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(unique = true)
    private Location location;

    @OneToMany(mappedBy = "thing", cascade = CascadeType.PERSIST)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<Device> devices = new HashSet<>();

    @OneToMany(mappedBy = "thing")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "thing" }, allowSetters = true)
    private Set<State> states = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "things" }, allowSetters = true)
    private ThingCategory thingCategory;

    @ManyToOne
    @JsonIgnoreProperties(value = { "things", "organisation" }, allowSetters = true)
    private Application application;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Thing id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Thing name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return this.location;
    }

    public Thing location(Location location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Set<Device> getDevices() {
        return this.devices;
    }

    public Thing devices(Set<Device> devices) {
        this.setDevices(devices);
        return this;
    }

    public Thing addDevice(Device device) {
        this.devices.add(device);
        device.setThing(this);
        return this;
    }

    public Thing removeDevice(Device device) {
        this.devices.remove(device);
        device.setThing(null);
        return this;
    }

    public void setDevices(Set<Device> devices) {
        if (this.devices != null) {
            this.devices.forEach(i -> i.setThing(null));
        }
        if (devices != null) {
            devices.forEach(i -> i.setThing(this));
        }
        this.devices = devices;
    }

    public Set<State> getStates() {
        return this.states;
    }

    public Thing states(Set<State> states) {
        this.setStates(states);
        return this;
    }

    public Thing addState(State state) {
        this.states.add(state);
        state.setThing(this);
        return this;
    }

    public Thing removeState(State state) {
        this.states.remove(state);
        state.setThing(null);
        return this;
    }

    public void setStates(Set<State> states) {
        if (this.states != null) {
            this.states.forEach(i -> i.setThing(null));
        }
        if (states != null) {
            states.forEach(i -> i.setThing(this));
        }
        this.states = states;
    }

    public ThingCategory getThingCategory() {
        return this.thingCategory;
    }

    public Thing thingCategory(ThingCategory thingCategory) {
        this.setThingCategory(thingCategory);
        return this;
    }

    public void setThingCategory(ThingCategory thingCategory) {
        this.thingCategory = thingCategory;
    }

    public Application getApplication() {
        return this.application;
    }

    public Thing application(Application application) {
        this.setApplication(application);
        return this;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Thing)) {
            return false;
        }
        return id != null && id.equals(((Thing) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Thing{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
