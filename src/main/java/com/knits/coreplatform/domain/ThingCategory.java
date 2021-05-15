package com.knits.coreplatform.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entity to group up Thing entitites.\n@author Vassili Moskaljov\n@version 1.0
 */
@Entity
@Table(name = "thing_category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ThingCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "thingCategory")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "location", "devices", "states", "thingCategory", "application" }, allowSetters = true)
    private Set<Thing> things = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ThingCategory id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public ThingCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public ThingCategory description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Thing> getThings() {
        return this.things;
    }

    public ThingCategory things(Set<Thing> things) {
        this.setThings(things);
        return this;
    }

    public ThingCategory addThing(Thing thing) {
        this.things.add(thing);
        thing.setThingCategory(this);
        return this;
    }

    public ThingCategory removeThing(Thing thing) {
        this.things.remove(thing);
        thing.setThingCategory(null);
        return this;
    }

    public void setThings(Set<Thing> things) {
        if (this.things != null) {
            this.things.forEach(i -> i.setThingCategory(null));
        }
        if (things != null) {
            things.forEach(i -> i.setThingCategory(this));
        }
        this.things = things;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ThingCategory)) {
            return false;
        }
        return id != null && id.equals(((ThingCategory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThingCategory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
