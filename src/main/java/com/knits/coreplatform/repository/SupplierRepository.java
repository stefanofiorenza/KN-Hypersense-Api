package com.knits.coreplatform.repository;

import com.knits.coreplatform.domain.Supplier;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Supplier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Supplier findByName(String name);
}
