package com.knits.coreplatform.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StatusMapperTest {

    private StatusMapper statusMapper;

    @BeforeEach
    public void setUp() {
        statusMapper = new StatusMapperImpl();
    }
}
