package com.knits.coreplatform.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.coreplatform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AlertConfigurationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlertConfiguration.class);
        AlertConfiguration alertConfiguration1 = new AlertConfiguration();
        alertConfiguration1.setId(1L);
        AlertConfiguration alertConfiguration2 = new AlertConfiguration();
        alertConfiguration2.setId(alertConfiguration1.getId());
        assertThat(alertConfiguration1).isEqualTo(alertConfiguration2);
        alertConfiguration2.setId(2L);
        assertThat(alertConfiguration1).isNotEqualTo(alertConfiguration2);
        alertConfiguration1.setId(null);
        assertThat(alertConfiguration1).isNotEqualTo(alertConfiguration2);
    }
}
