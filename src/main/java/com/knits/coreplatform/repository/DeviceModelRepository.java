package com.knits.coreplatform.repository;

import com.knits.coreplatform.domain.DeviceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DeviceModel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeviceModelRepository extends JpaRepository<DeviceModel, Long> {
    DeviceModel findByName(String name);
}
