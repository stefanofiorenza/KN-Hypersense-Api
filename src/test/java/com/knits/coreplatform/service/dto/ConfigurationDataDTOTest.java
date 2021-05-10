package com.knits.coreplatform.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.coreplatform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConfigurationDataDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConfigurationDataDTO.class);
        ConfigurationDataDTO configurationDataDTO1 = new ConfigurationDataDTO();
        configurationDataDTO1.setId(1L);
        ConfigurationDataDTO configurationDataDTO2 = new ConfigurationDataDTO();
        assertThat(configurationDataDTO1).isNotEqualTo(configurationDataDTO2);
        configurationDataDTO2.setId(configurationDataDTO1.getId());
        assertThat(configurationDataDTO1).isEqualTo(configurationDataDTO2);
        configurationDataDTO2.setId(2L);
        assertThat(configurationDataDTO1).isNotEqualTo(configurationDataDTO2);
        configurationDataDTO1.setId(null);
        assertThat(configurationDataDTO1).isNotEqualTo(configurationDataDTO2);
    }
}
