package com.knits.coreplatform.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.coreplatform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RuleConfigurationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RuleConfiguration.class);
        RuleConfiguration ruleConfiguration1 = new RuleConfiguration();
        ruleConfiguration1.setId(1L);
        RuleConfiguration ruleConfiguration2 = new RuleConfiguration();
        ruleConfiguration2.setId(ruleConfiguration1.getId());
        assertThat(ruleConfiguration1).isEqualTo(ruleConfiguration2);
        ruleConfiguration2.setId(2L);
        assertThat(ruleConfiguration1).isNotEqualTo(ruleConfiguration2);
        ruleConfiguration1.setId(null);
        assertThat(ruleConfiguration1).isNotEqualTo(ruleConfiguration2);
    }
}
