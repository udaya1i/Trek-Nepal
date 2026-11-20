package com.trek.admin.dto.request.requestValidator;

import com.trek.admin.dto.command.WayPointAddCommand;
import com.trek.admin.dto.request.TrekWaypointAddRequest;
import com.trek.core.admin.service.DestinationQueryService;
import com.trek.core.admin.service.MediaService;
import com.trek.core.entity.TrekStrategy;
import com.trek.core.admin.service.TrekItineraryQueryService;
import com.trek.core.admin.service.TrekStrategyQueryService;
import com.trek.core.util.RequestValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrekWaypointAddRequestValidator implements RequestValidator<TrekWaypointAddRequest, WayPointAddCommand> {

    private final TrekStrategyQueryService trekStrategyQueryService;
    private final TrekItineraryQueryService trekItineraryQueryService;
    private final DestinationQueryService destinationQueryService;
    private final MediaService imageService;

    @Override
    public WayPointAddCommand validate(TrekWaypointAddRequest request) {

        WayPointAddCommand command = new WayPointAddCommand();

        if (request.getStrategy() != null && request.getStrategy() > 0) {
            command.setStrategy(trekStrategyQueryService.getById(request.getStrategy()));
        }

        if (request.getTrekItinerary() != null && request.getTrekItinerary() > 0 ) {
            command.setTrekItinerary(trekItineraryQueryService.getById(request.getTrekItinerary()));
        }

        if (request.getDestination() != null && request.getDestination() > 0) {
            command.setDestination(destinationQueryService.getById(request.getDestination()));
        }

        command.setImages(imageService.finaAllByUrl(request.getImages()));
        command.setSequenceOrder(request.getSequenceOrder());
        command.setName(request.getName());
        command.setLatitude(request.getLatitude());
        command.setLatitudeNumeric(request.getLatitudeNumeric());
        command.setLongitude(request.getLongitude());
        command.setLongitudeNumeric(request.getLongitudeNumeric());
        command.setAltitude(request.getAltitude());
        command.setAltitudeNumeric(request.getAltitudeNumeric());
        command.setType(request.getType());
        command.setShortDescription(request.getShortDescription());
        command.setFullDescription(request.getFullDescription());
        command.setHasWifi(request.getHasWifi());
        command.setHasCharging(request.getHasCharging());
        command.setHasHotShower(request.getHasHotShower());
        command.setHasAtm(request.getHasAtm());
        command.setSignificance(request.getSignificance());

        return command;
    }
}