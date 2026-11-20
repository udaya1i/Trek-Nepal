package com.trek.core.repository;

import com.trek.core.constants.TrekkerStatus;
import com.trek.core.entity.Trekker;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrekkerRepository extends AbstractRepository<Trekker, Long> {
    public Optional<Trekker> findByUsername(String username);

    public boolean existsByUsernameAndStatus(String username, TrekkerStatus trekkerStatus);

    public boolean existsByEmailAndStatus(String username, TrekkerStatus trekkerStatus);

    long countByStatus(TrekkerStatus status);

    public Optional<Trekker> findByEmail(String email);

    Optional<Trekker> findByGoogleId(String googleId);

}
