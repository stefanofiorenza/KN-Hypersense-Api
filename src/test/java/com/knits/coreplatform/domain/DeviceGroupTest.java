package com.knits.coreplatform.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.coreplatform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DeviceGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeviceGroup.class);
        DeviceGroup deviceGroup1 = new DeviceGroup();
        deviceGroup1.setId(1L);
        DeviceGroup deviceGroup2 = new DeviceGroup();
        deviceGroup2.setId(deviceGroup1.getId());
        assertThat(deviceGroup1).isEqualTo(deviceGroup2);
        deviceGroup2.setId(2L);
        assertThat(deviceGroup1).isNotEqualTo(deviceGroup2);
        deviceGroup1.setId(null);
        assertThat(deviceGroup1).isNotEqualTo(deviceGroup2);
    }
}
