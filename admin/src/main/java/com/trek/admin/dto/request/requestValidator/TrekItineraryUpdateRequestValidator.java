package com.trek.admin.dto.request.requestValidator;

import com.trek.admin.dto.command.TrekItineraryUpdateCommand;
import com.trek.admin.dto.request.v2.TrekItineraryUpdateRequest;
import com.trek.core.admin.service.TrekItineraryQueryService;
import com.trek.core.entity.TrekItinerary;
import com.trek.core.util.RequestValidator;
import com.trek.core.util.UpdateUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrekItineraryUpdateRequestValidator implements RequestValidator<TrekItineraryUpdateRequest, TrekItineraryUpdateCommand> {

    private final TrekItineraryQueryService trekItineraryQueryService;

    @Override
    public TrekItineraryUpdateCommand validate(TrekItineraryUpdateRequest request) {

        TrekItinerary trekItinerary = trekItineraryQueryService.getById(request.getId());

        TrekItineraryUpdateCommand command = new TrekItineraryUpdateCommand();
        command.setTrekItinerary(trekItinerary);
        command.setDayNumber(UpdateUtil.getOrDefault(trekItinerary.getDayNumber(), request.getDayNumber()));
        command.setDayType(UpdateUtil.getOrDefault(trekItinerary.getDayType(), request.getDayType()));
        command.setStartLocation(UpdateUtil.getOrDefault(trekItinerary.getStartLocation(), request.getStartLocation()));
        command.setEndLocation(UpdateUtil.getOrDefault(trekItinerary.getEndLocation(), request.getEndLocation()));
        command.setDistanceKm(UpdateUtil.getOrDefault(trekItinerary.getDistanceKm(), request.getDistanceKm()));
        command.setDistanceKmNumeric(UpdateUtil.getOrDefault(trekItinerary.getDistanceKmNumeric(), request.getDistanceKmNumeric()));
        command.setDurationHours(UpdateUtil.getOrDefault(trekItinerary.getDurationHours(), request.getDurationHours()));
        command.setAltitudeGain(UpdateUtil.getOrDefault(trekItinerary.getAltitudeGain(), request.getAltitudeGain()));
        command.setAltitudeLoss(UpdateUtil.getOrDefault(trekItinerary.getAltitudeLoss(), request.getAltitudeLoss()));
        command.setShortDescription(UpdateUtil.getOrDefault(trekItinerary.getShortDescription(), request.getShortDescription()));
        command.setFullDescription(UpdateUtil.getOrDefault(trekItinerary.getFullDescription(), request.getFullDescription()));
        command.setFlexibilityNotes(UpdateUtil.getOrDefault(trekItinerary.getFlexibilityNotes(), request.getFlexibilityNotes()));

        return command;
    }
}