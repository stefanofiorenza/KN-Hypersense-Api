package com.knits.coreplatform.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConfigurationDataMapperTest {

    private ConfigurationDataMapper configurationDataMapper;

    @BeforeEach
    public void setUp() {
        configurationDataMapper = new ConfigurationDataMapperImpl();
    }
}
