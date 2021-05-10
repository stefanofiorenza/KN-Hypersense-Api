package com.knits.coreplatform.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.coreplatform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AlertConfigurationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlertConfigurationDTO.class);
        AlertConfigurationDTO alertConfigurationDTO1 = new AlertConfigurationDTO();
        alertConfigurationDTO1.setId(1L);
        AlertConfigurationDTO alertConfigurationDTO2 = new AlertConfigurationDTO();
        assertThat(alertConfigurationDTO1).isNotEqualTo(alertConfigurationDTO2);
        alertConfigurationDTO2.setId(alertConfigurationDTO1.getId());
        assertThat(alertConfigurationDTO1).isEqualTo(alertConfigurationDTO2);
        alertConfigurationDTO2.setId(2L);
        assertThat(alertConfigurationDTO1).isNotEqualTo(alertConfigurationDTO2);
        alertConfigurationDTO1.setId(null);
        assertThat(alertConfigurationDTO1).isNotEqualTo(alertConfigurationDTO2);
    }
}
