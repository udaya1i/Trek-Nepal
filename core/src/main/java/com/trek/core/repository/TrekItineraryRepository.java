package com.trek.core.repository;

import com.trek.core.entity.TrekItinerary;
import com.trek.core.entity.TrekStrategy;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrekItineraryRepository extends AbstractRepository<TrekItinerary, Long> {

    public List<TrekItinerary> findByTrekStrategy(TrekStrategy trek);

}
 