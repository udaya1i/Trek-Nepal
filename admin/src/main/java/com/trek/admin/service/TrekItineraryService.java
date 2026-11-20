package com.trek.admin.service;

import com.trek.admin.dto.command.TrekItineraryCommand;
import com.trek.admin.dto.command.TrekItineraryUpdateCommand;
import com.trek.core.dto.response.TrekItineraryDetailResponse;
import com.trek.core.entity.TrekItinerary;
import com.trek.core.repository.TrekItineraryRepository;
import com.trek.core.admin.service.TrekItineraryQueryService;
import com.trek.core.admin.service.TrekStrategyQueryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class TrekItineraryService {

    private final TrekItineraryQueryService itineraryQueryService;
    private final TrekItineraryRepository trekItineraryRepository;
    private final TrekStrategyQueryService trekStrategyRepository;

    public TrekItineraryDetailResponse addItinerary(TrekItineraryCommand command) {
        TrekItinerary trekItinerary = new TrekItinerary();

        trekItinerary.setDayNumber(command.getDayNumber());
        trekItinerary.setDayType(command.getDayType());
        trekItinerary.setStartLocation(command.getStartLocation());
        trekItinerary.setEndLocation(command.getEndLocation());
        trekItinerary.setDistanceKm(command.getDistanceKm());
        trekItinerary.setDistanceKmNumeric(command.getDistanceKmNumeric());
        trekItinerary.setDurationHours(command.getDurationHours());
        trekItinerary.setAltitudeGain(command.getAltitudeGain());
        trekItinerary.setAltitudeLoss(command.getAltitudeLoss());
        trekItinerary.setShortDescription(command.getShortDescription());
        trekItinerary.setFullDescription(command.getFullDescription());
        trekItinerary.setFlexibilityNotes(command.getFlexibilityNotes());
        trekItinerary.setTrekStrategy(command.getStrategy());

        return TrekItineraryDetailResponse.of(trekItineraryRepository.save(trekItinerary));
    }

    public TrekItineraryDetailResponse updateItinerary(TrekItineraryUpdateCommand command) {
        TrekItinerary trekItinerary = command.getTrekItinerary();

        trekItinerary.setDayNumber(command.getDayNumber());
        trekItinerary.setDayType(command.getDayType());
        trekItinerary.setStartLocation(command.getStartLocation());
        trekItinerary.setEndLocation(command.getEndLocation());
        trekItinerary.setDistanceKm(command.getDistanceKm());
        trekItinerary.setDistanceKmNumeric(command.getDistanceKmNumeric());
        trekItinerary.setDurationHours(command.getDurationHours());
        trekItinerary.setAltitudeGain(command.getAltitudeGain());
        trekItinerary.setAltitudeLoss(command.getAltitudeLoss());
        trekItinerary.setShortDescription(command.getShortDescription());
        trekItinerary.setFullDescription(command.getFullDescription());
        trekItinerary.setFlexibilityNotes(command.getFlexibilityNotes());

        return TrekItineraryDetailResponse.of(trekItineraryRepository.save(trekItinerary));
    }

    public List<TrekItineraryDetailResponse> getAllByStrategy(Long id) {
        return TrekItineraryDetailResponse.of(trekItineraryRepository.findByTrekStrategy(trekStrategyRepository.getById(id)));
    }

    public TrekItinerary getById(Long trekId) {
        return itineraryQueryService.getById(trekId);
    }
}
