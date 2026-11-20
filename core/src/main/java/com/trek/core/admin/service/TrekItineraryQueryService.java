package com.trek.core.admin.service;

import com.trek.core.entity.TrekItinerary;
import com.trek.core.entity.TrekStrategy;
import com.trek.core.repository.TrekItineraryRepository;
import com.trek.core.exception.RequestValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrekItineraryQueryService {

    private final TrekItineraryRepository trekItineraryRepository;

    public TrekItinerary getById(Long id) {
        return trekItineraryRepository.findById(id)
                .orElseThrow(() -> new RequestValidationException("Invalid itinerary"));
    }

    public List<TrekItinerary> getAll() {
        return trekItineraryRepository.findAll();
    }

    public List<TrekItinerary> getByStrategy(TrekStrategy strategy) {
        return trekItineraryRepository.findByTrekStrategy(strategy);
    }

}
