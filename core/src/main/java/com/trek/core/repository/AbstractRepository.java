package com.trek.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @param <T>
 * @param <ID>
 * @author udaya
 */

@NoRepositoryBean
public interface AbstractRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
}
