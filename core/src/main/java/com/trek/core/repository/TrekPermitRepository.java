package com.trek.core.repository;

import com.trek.core.entity.Destination;
import com.trek.core.entity.Permit;
import com.trek.core.entity.TrekStrategy;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrekPermitRepository extends AbstractRepository<Permit, Long> {

    public List<Permit> findByDestination(Destination destination);

    Optional<Permit> findBySlug(String slug);

    boolean existsBySlug(String slug);

    List<Permit> findBySlugIsNull();
}
