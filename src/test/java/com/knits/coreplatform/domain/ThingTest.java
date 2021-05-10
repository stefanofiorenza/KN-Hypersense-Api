package com.knits.coreplatform.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.coreplatform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ThingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Thing.class);
        Thing thing1 = new Thing();
        thing1.setId(1L);
        Thing thing2 = new Thing();
        thing2.setId(thing1.getId());
        assertThat(thing1).isEqualTo(thing2);
        thing2.setId(2L);
        assertThat(thing1).isNotEqualTo(thing2);
        thing1.setId(null);
        assertThat(thing1).isNotEqualTo(thing2);
    }
}
