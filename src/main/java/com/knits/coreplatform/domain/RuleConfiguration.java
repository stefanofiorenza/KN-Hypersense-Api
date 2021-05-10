package com.knits.coreplatform.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entity to configure certain rule\n@author Vassili Moskaljov\n@version 1.0
 */
@Entity
@Table(name = "rule_configuration")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RuleConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = { "ruleConfigurations", "device" }, allowSetters = true)
    private Rule rule;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RuleConfiguration id(Long id) {
        this.id = id;
        return this;
    }

    public Rule getRule() {
        return this.rule;
    }

    public RuleConfiguration rule(Rule rule) {
        this.setRule(rule);
        return this;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RuleConfiguration)) {
            return false;
        }
        return id != null && id.equals(((RuleConfiguration) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RuleConfiguration{" +
            "id=" + getId() +
            "}";
    }
}
