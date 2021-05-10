package com.knits.coreplatform.repository;

import com.knits.coreplatform.domain.AlertConfiguration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AlertConfiguration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlertConfigurationRepository extends JpaRepository<AlertConfiguration, Long> {}
