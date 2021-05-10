package com.knits.coreplatform.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.coreplatform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TelemetryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TelemetryDTO.class);
        TelemetryDTO telemetryDTO1 = new TelemetryDTO();
        telemetryDTO1.setId(1L);
        TelemetryDTO telemetryDTO2 = new TelemetryDTO();
        assertThat(telemetryDTO1).isNotEqualTo(telemetryDTO2);
        telemetryDTO2.setId(telemetryDTO1.getId());
        assertThat(telemetryDTO1).isEqualTo(telemetryDTO2);
        telemetryDTO2.setId(2L);
        assertThat(telemetryDTO1).isNotEqualTo(telemetryDTO2);
        telemetryDTO1.setId(null);
        assertThat(telemetryDTO1).isNotEqualTo(telemetryDTO2);
    }
}
