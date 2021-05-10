package com.knits.coreplatform.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RuleMapperTest {

    private RuleMapper ruleMapper;

    @BeforeEach
    public void setUp() {
        ruleMapper = new RuleMapperImpl();
    }
}
