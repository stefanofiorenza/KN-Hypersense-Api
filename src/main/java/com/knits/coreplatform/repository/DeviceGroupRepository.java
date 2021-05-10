package com.knits.coreplatform.repository;

import com.knits.coreplatform.domain.DeviceGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DeviceGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeviceGroupRepository extends JpaRepository<DeviceGroup, Long> {}
