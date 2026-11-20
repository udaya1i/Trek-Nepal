package com.trek.core.repository;

import com.trek.core.entity.Authority;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminAuthorityRepository extends AbstractRepository<Authority, Long> {
}
