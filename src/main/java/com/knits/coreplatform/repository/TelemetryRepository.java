package com.knits.coreplatform.repository;

import com.knits.coreplatform.domain.Telemetry;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Telemetry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TelemetryRepository extends JpaRepository<Telemetry, Long> {
    Telemetry findByName(String name);
}
