package com.knits.coreplatform.repository;

import com.knits.coreplatform.domain.AlertMessage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AlertMessage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlertMessageRepository extends JpaRepository<AlertMessage, Long> {
    AlertMessage findByName(String name);
}
