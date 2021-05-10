package com.knits.coreplatform.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.coreplatform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ThingCategoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThingCategoryDTO.class);
        ThingCategoryDTO thingCategoryDTO1 = new ThingCategoryDTO();
        thingCategoryDTO1.setId(1L);
        ThingCategoryDTO thingCategoryDTO2 = new ThingCategoryDTO();
        assertThat(thingCategoryDTO1).isNotEqualTo(thingCategoryDTO2);
        thingCategoryDTO2.setId(thingCategoryDTO1.getId());
        assertThat(thingCategoryDTO1).isEqualTo(thingCategoryDTO2);
        thingCategoryDTO2.setId(2L);
        assertThat(thingCategoryDTO1).isNotEqualTo(thingCategoryDTO2);
        thingCategoryDTO1.setId(null);
        assertThat(thingCategoryDTO1).isNotEqualTo(thingCategoryDTO2);
    }
}
