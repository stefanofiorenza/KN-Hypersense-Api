package com.knits.coreplatform.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TelemetryMapperTest {

    private TelemetryMapper telemetryMapper;

    @BeforeEach
    public void setUp() {
        telemetryMapper = new TelemetryMapperImpl();
    }
}
