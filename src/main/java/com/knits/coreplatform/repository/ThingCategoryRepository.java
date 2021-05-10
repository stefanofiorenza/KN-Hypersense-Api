package com.knits.coreplatform.repository;

import com.knits.coreplatform.domain.ThingCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ThingCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThingCategoryRepository extends JpaRepository<ThingCategory, Long> {}
