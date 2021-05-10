package com.knits.coreplatform.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.coreplatform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AlertMessageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlertMessageDTO.class);
        AlertMessageDTO alertMessageDTO1 = new AlertMessageDTO();
        alertMessageDTO1.setId(1L);
        AlertMessageDTO alertMessageDTO2 = new AlertMessageDTO();
        assertThat(alertMessageDTO1).isNotEqualTo(alertMessageDTO2);
        alertMessageDTO2.setId(alertMessageDTO1.getId());
        assertThat(alertMessageDTO1).isEqualTo(alertMessageDTO2);
        alertMessageDTO2.setId(2L);
        assertThat(alertMessageDTO1).isNotEqualTo(alertMessageDTO2);
        alertMessageDTO1.setId(null);
        assertThat(alertMessageDTO1).isNotEqualTo(alertMessageDTO2);
    }
}
