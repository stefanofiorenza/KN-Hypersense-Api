package com.knits.coreplatform.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.coreplatform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConfigurationDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConfigurationData.class);
        ConfigurationData configurationData1 = new ConfigurationData();
        configurationData1.setId(1L);
        ConfigurationData configurationData2 = new ConfigurationData();
        configurationData2.setId(configurationData1.getId());
        assertThat(configurationData1).isEqualTo(configurationData2);
        configurationData2.setId(2L);
        assertThat(configurationData1).isNotEqualTo(configurationData2);
        configurationData1.setId(null);
        assertThat(configurationData1).isNotEqualTo(configurationData2);
    }
}
