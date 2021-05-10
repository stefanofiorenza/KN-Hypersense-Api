package com.knits.coreplatform.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ThingCategoryMapperTest {

    private ThingCategoryMapper thingCategoryMapper;

    @BeforeEach
    public void setUp() {
        thingCategoryMapper = new ThingCategoryMapperImpl();
    }
}
