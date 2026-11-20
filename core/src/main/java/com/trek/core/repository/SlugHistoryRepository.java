package com.trek.core.repository;

import com.trek.core.constants.SluggableType;
import com.trek.core.entity.SlugHistory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SlugHistoryRepository extends AbstractRepository<SlugHistory, Long> {

    Optional<SlugHistory> findByOwnerTypeAndOldSlug(SluggableType ownerType, String oldSlug);
}
