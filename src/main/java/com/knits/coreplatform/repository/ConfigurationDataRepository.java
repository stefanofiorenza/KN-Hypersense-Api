package com.knits.coreplatform.repository;

import com.knits.coreplatform.domain.ConfigurationData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ConfigurationData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfigurationDataRepository extends JpaRepository<ConfigurationData, Long> {}
