package com.knits.coreplatform.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ThingMapperTest {

    private ThingMapper thingMapper;

    @BeforeEach
    public void setUp() {
        thingMapper = new ThingMapperImpl();
    }
}
