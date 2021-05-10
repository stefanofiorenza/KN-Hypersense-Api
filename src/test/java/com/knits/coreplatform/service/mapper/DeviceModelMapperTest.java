package com.knits.coreplatform.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeviceModelMapperTest {

    private DeviceModelMapper deviceModelMapper;

    @BeforeEach
    public void setUp() {
        deviceModelMapper = new DeviceModelMapperImpl();
    }
}
