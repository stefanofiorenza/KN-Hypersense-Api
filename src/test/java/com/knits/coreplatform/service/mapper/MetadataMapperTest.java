package com.knits.coreplatform.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MetadataMapperTest {

    private MetadataMapper metadataMapper;

    @BeforeEach
    public void setUp() {
        metadataMapper = new MetadataMapperImpl();
    }
}
