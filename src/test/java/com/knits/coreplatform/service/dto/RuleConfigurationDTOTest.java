package com.knits.coreplatform.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.coreplatform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RuleConfigurationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RuleConfigurationDTO.class);
        RuleConfigurationDTO ruleConfigurationDTO1 = new RuleConfigurationDTO();
        ruleConfigurationDTO1.setId(1L);
        RuleConfigurationDTO ruleConfigurationDTO2 = new RuleConfigurationDTO();
        assertThat(ruleConfigurationDTO1).isNotEqualTo(ruleConfigurationDTO2);
        ruleConfigurationDTO2.setId(ruleConfigurationDTO1.getId());
        assertThat(ruleConfigurationDTO1).isEqualTo(ruleConfigurationDTO2);
        ruleConfigurationDTO2.setId(2L);
        assertThat(ruleConfigurationDTO1).isNotEqualTo(ruleConfigurationDTO2);
        ruleConfigurationDTO1.setId(null);
        assertThat(ruleConfigurationDTO1).isNotEqualTo(ruleConfigurationDTO2);
    }
}
