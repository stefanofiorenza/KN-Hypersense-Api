package com.knits.coreplatform.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.coreplatform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DeviceGroupDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeviceGroupDTO.class);
        DeviceGroupDTO deviceGroupDTO1 = new DeviceGroupDTO();
        deviceGroupDTO1.setId(1L);
        DeviceGroupDTO deviceGroupDTO2 = new DeviceGroupDTO();
        assertThat(deviceGroupDTO1).isNotEqualTo(deviceGroupDTO2);
        deviceGroupDTO2.setId(deviceGroupDTO1.getId());
        assertThat(deviceGroupDTO1).isEqualTo(deviceGroupDTO2);
        deviceGroupDTO2.setId(2L);
        assertThat(deviceGroupDTO1).isNotEqualTo(deviceGroupDTO2);
        deviceGroupDTO1.setId(null);
        assertThat(deviceGroupDTO1).isNotEqualTo(deviceGroupDTO2);
    }
}
