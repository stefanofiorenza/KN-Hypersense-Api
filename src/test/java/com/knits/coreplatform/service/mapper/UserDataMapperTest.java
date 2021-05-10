package com.knits.coreplatform.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserDataMapperTest {

    private UserDataMapper userDataMapper;

    @BeforeEach
    public void setUp() {
        userDataMapper = new UserDataMapperImpl();
    }
}
