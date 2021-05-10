package com.knits.coreplatform.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeviceGroupMapperTest {

    private DeviceGroupMapper deviceGroupMapper;

    @BeforeEach
    public void setUp() {
        deviceGroupMapper = new DeviceGroupMapperImpl();
    }
}
