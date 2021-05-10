package com.knits.coreplatform.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AlertMessageMapperTest {

    private AlertMessageMapper alertMessageMapper;

    @BeforeEach
    public void setUp() {
        alertMessageMapper = new AlertMessageMapperImpl();
    }
}
