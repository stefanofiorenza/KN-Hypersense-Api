package com.knits.coreplatform.repository;

import com.knits.coreplatform.domain.RuleConfiguration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RuleConfiguration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RuleConfigurationRepository extends JpaRepository<RuleConfiguration, Long> {}
