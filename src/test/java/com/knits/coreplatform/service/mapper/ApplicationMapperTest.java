package com.knits.coreplatform.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplicationMapperTest {

    private ApplicationMapper applicationMapper;

    @BeforeEach
    public void setUp() {
        applicationMapper = new ApplicationMapperImpl();
    }
}
