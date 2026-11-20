package com.trek.core.repository;

import com.trek.core.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author udaya
 */

@Repository
public interface RoleRepository extends AbstractRepository<Role, Long> {

    boolean existsByName(String name);

    Optional<Role> findByName(String name);
}
