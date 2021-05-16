package com.knits.coreplatform.repository;

import com.knits.coreplatform.domain.Thing;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Thing entity.
 */
@Repository
public interface ThingRepository extends JpaRepository<Thing, Long> {
    @Query(
        value = "select distinct thing from Thing thing left join fetch thing.states",
        countQuery = "select count(distinct thing) from Thing thing"
    )
    Page<Thing> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct thing from Thing thing left join fetch thing.states")
    List<Thing> findAllWithEagerRelationships();

    @Query("select thing from Thing thing left join fetch thing.states where thing.id =:id")
    Optional<Thing> findOneWithEagerRelationships(@Param("id") Long id);
}
