package com.knits.coreplatform.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.coreplatform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ThingCategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThingCategory.class);
        ThingCategory thingCategory1 = new ThingCategory();
        thingCategory1.setId(1L);
        ThingCategory thingCategory2 = new ThingCategory();
        thingCategory2.setId(thingCategory1.getId());
        assertThat(thingCategory1).isEqualTo(thingCategory2);
        thingCategory2.setId(2L);
        assertThat(thingCategory1).isNotEqualTo(thingCategory2);
        thingCategory1.setId(null);
        assertThat(thingCategory1).isNotEqualTo(thingCategory2);
    }
}
