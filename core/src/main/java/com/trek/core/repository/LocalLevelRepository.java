package com.trek.core.repository;

import com.trek.core.entity.District;
import com.trek.core.entity.LocalLevel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalLevelRepository extends AbstractRepository<LocalLevel, Long> {
    public List<LocalLevel> getByDistrict(District district);
}
