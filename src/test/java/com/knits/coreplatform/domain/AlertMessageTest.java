package com.knits.coreplatform.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.coreplatform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AlertMessageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlertMessage.class);
        AlertMessage alertMessage1 = new AlertMessage();
        alertMessage1.setId(1L);
        AlertMessage alertMessage2 = new AlertMessage();
        alertMessage2.setId(alertMessage1.getId());
        assertThat(alertMessage1).isEqualTo(alertMessage2);
        alertMessage2.setId(2L);
        assertThat(alertMessage1).isNotEqualTo(alertMessage2);
        alertMessage1.setId(null);
        assertThat(alertMessage1).isNotEqualTo(alertMessage2);
    }
}
