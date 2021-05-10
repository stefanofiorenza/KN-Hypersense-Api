package com.knits.coreplatform.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.coreplatform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ThingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThingDTO.class);
        ThingDTO thingDTO1 = new ThingDTO();
        thingDTO1.setId(1L);
        ThingDTO thingDTO2 = new ThingDTO();
        assertThat(thingDTO1).isNotEqualTo(thingDTO2);
        thingDTO2.setId(thingDTO1.getId());
        assertThat(thingDTO1).isEqualTo(thingDTO2);
        thingDTO2.setId(2L);
        assertThat(thingDTO1).isNotEqualTo(thingDTO2);
        thingDTO1.setId(null);
        assertThat(thingDTO1).isNotEqualTo(thingDTO2);
    }
}
