package com.knits.coreplatform.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AlertConfigurationMapperTest {

    private AlertConfigurationMapper alertConfigurationMapper;

    @BeforeEach
    public void setUp() {
        alertConfigurationMapper = new AlertConfigurationMapperImpl();
    }
}
