package com.knits.coreplatform.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RuleConfigurationMapperTest {

    private RuleConfigurationMapper ruleConfigurationMapper;

    @BeforeEach
    public void setUp() {
        ruleConfigurationMapper = new RuleConfigurationMapperImpl();
    }
}
