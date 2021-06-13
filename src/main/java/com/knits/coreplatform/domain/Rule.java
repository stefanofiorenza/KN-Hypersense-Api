package com.knits.coreplatform.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entity for Optional information for custom Action that sensors can define.\n@author Vassili Moskaljov\n@version 1.0
 */
@Entity
@Table(name = "rule")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Rule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "alias")
    private String alias;

    @OneToMany(mappedBy = "rule")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "rule" }, allowSetters = true)
    private Set<RuleConfiguration> ruleConfigurations = new HashSet<>();

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

    public Rule id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Rule name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Rule description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAlias() {
        return this.alias;
    }

    public Rule alias(String alias) {
        this.alias = alias;
        return this;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Set<RuleConfiguration> getRuleConfigurations() {
        return this.ruleConfigurations;
    }

    public Rule ruleConfigurations(Set<RuleConfiguration> ruleConfigurations) {
        this.setRuleConfigurations(ruleConfigurations);
        return this;
    }

    public Rule addRuleConfiguration(RuleConfiguration ruleConfiguration) {
        this.ruleConfigurations.add(ruleConfiguration);
        ruleConfiguration.setRule(this);
        return this;
    }

    public Rule removeRuleConfiguration(RuleConfiguration ruleConfiguration) {
        this.ruleConfigurations.remove(ruleConfiguration);
        ruleConfiguration.setRule(null);
        return this;
    }

    public void setRuleConfigurations(Set<RuleConfiguration> ruleConfigurations) {
        if (this.ruleConfigurations != null) {
            this.ruleConfigurations.forEach(i -> i.setRule(null));
        }
        if (ruleConfigurations != null) {
            ruleConfigurations.forEach(i -> i.setRule(this));
        }
        this.ruleConfigurations = ruleConfigurations;
    }

    public Device getDevice() {
        return this.device;
    }

    public Rule device(Device device) {
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
        if (!(o instanceof Rule)) {
            return false;
        }
        return id != null && id.equals(((Rule) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rule{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", alias='" + getAlias() + "'" +
            "}";
    }
}
