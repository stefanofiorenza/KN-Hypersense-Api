package com.knits.coreplatform.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.coreplatform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DeviceConfigurationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeviceConfigurationDTO.class);
        DeviceConfigurationDTO deviceConfigurationDTO1 = new DeviceConfigurationDTO();
        deviceConfigurationDTO1.setId(1L);
        DeviceConfigurationDTO deviceConfigurationDTO2 = new DeviceConfigurationDTO();
        assertThat(deviceConfigurationDTO1).isNotEqualTo(deviceConfigurationDTO2);
        deviceConfigurationDTO2.setId(deviceConfigurationDTO1.getId());
        assertThat(deviceConfigurationDTO1).isEqualTo(deviceConfigurationDTO2);
        deviceConfigurationDTO2.setId(2L);
        assertThat(deviceConfigurationDTO1).isNotEqualTo(deviceConfigurationDTO2);
        deviceConfigurationDTO1.setId(null);
        assertThat(deviceConfigurationDTO1).isNotEqualTo(deviceConfigurationDTO2);
    }
}
