package com.knits.coreplatform.repository;

import com.knits.coreplatform.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Status entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    Status findByName(String name);
}
