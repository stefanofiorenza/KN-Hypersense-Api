package com.knits.coreplatform.repository;

import com.knits.coreplatform.domain.Application;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Application entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findAllByIsAuthorizedTrue();
}
