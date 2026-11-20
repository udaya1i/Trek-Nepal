package com.trek.core.repository;

import com.trek.core.entity.TrekPacking;
import com.trek.core.entity.TrekStrategy;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrekPackagingRepository extends AbstractRepository<TrekPacking, Long> {

    public List<TrekPacking> getByTrekStrategy(TrekStrategy trek);
}
