package com.knits.coreplatform.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.knits.coreplatform.domain.RuleConfiguration} entity.
 */
@ApiModel(description = "Entity to configure certain rule\n@author Vassili Moskaljov\n@version 1.0")
public class RuleConfigurationDTO implements Serializable {

    private Long id;

    private RuleDTO rule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RuleDTO getRule() {
        return rule;
    }

    public void setRule(RuleDTO rule) {
        this.rule = rule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RuleConfigurationDTO)) {
            return false;
        }

        RuleConfigurationDTO ruleConfigurationDTO = (RuleConfigurationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ruleConfigurationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RuleConfigurationDTO{" +
            "id=" + getId() +
            ", rule=" + getRule() +
            "}";
    }
}
