package com.knits.coreplatform.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.coreplatform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserDataDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserDataDTO.class);
        UserDataDTO userDataDTO1 = new UserDataDTO();
        userDataDTO1.setId(1L);
        UserDataDTO userDataDTO2 = new UserDataDTO();
        assertThat(userDataDTO1).isNotEqualTo(userDataDTO2);
        userDataDTO2.setId(userDataDTO1.getId());
        assertThat(userDataDTO1).isEqualTo(userDataDTO2);
        userDataDTO2.setId(2L);
        assertThat(userDataDTO1).isNotEqualTo(userDataDTO2);
        userDataDTO1.setId(null);
        assertThat(userDataDTO1).isNotEqualTo(userDataDTO2);
    }
}
