package com.knits.coreplatform.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.coreplatform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TelemetryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Telemetry.class);
        Telemetry telemetry1 = new Telemetry();
        telemetry1.setId(1L);
        Telemetry telemetry2 = new Telemetry();
        telemetry2.setId(telemetry1.getId());
        assertThat(telemetry1).isEqualTo(telemetry2);
        telemetry2.setId(2L);
        assertThat(telemetry1).isNotEqualTo(telemetry2);
        telemetry1.setId(null);
        assertThat(telemetry1).isNotEqualTo(telemetry2);
    }
}
