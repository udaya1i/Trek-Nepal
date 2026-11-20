package com.trek.admin.dto.request.requestValidator;

import com.trek.admin.dto.command.TrekItineraryCommand;
import com.trek.admin.dto.request.TrekItineraryRequest;
import com.trek.core.entity.TrekStrategy;
import com.trek.core.admin.service.TrekStrategyQueryService;
import com.trek.core.util.RequestValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrekItineraryRequestValidator implements RequestValidator<TrekItineraryRequest, TrekItineraryCommand> {

    private final TrekStrategyQueryService trekStrategyQueryService;

    @Override
    public TrekItineraryCommand validate(TrekItineraryRequest request) {

        TrekStrategy strategy = trekStrategyQueryService.getById(request.getStrategyId());

        TrekItineraryCommand command = new TrekItineraryCommand();

        command.setStrategy(strategy);
        command.setDayNumber(request.getDayNumber());
        command.setDayType(request.getDayType());
        command.setStartLocation(request.getStartLocation());
        command.setEndLocation(request.getEndLocation());
        command.setDistanceKm(request.getDistanceKm());
        command.setDistanceKmNumeric(request.getDistanceKmNumeric());
        command.setDurationHours(request.getDurationHours());
        command.setAltitudeGain(request.getAltitudeGain());
        command.setAltitudeLoss(request.getAltitudeLoss());
        command.setShortDescription(request.getShortDescription());
        command.setFullDescription(request.getFullDescription());
        command.setFlexibilityNotes(request.getFlexibilityNotes());

        return command;
    }
}
