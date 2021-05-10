package com.knits.coreplatform.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeviceConfigurationMapperTest {

    private DeviceConfigurationMapper deviceConfigurationMapper;

    @BeforeEach
    public void setUp() {
        deviceConfigurationMapper = new DeviceConfigurationMapperImpl();
    }
}
