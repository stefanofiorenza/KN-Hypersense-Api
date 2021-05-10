package com.knits.coreplatform.repository;

import com.knits.coreplatform.domain.DeviceConfiguration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DeviceConfiguration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeviceConfigurationRepository extends JpaRepository<DeviceConfiguration, Long> {}
