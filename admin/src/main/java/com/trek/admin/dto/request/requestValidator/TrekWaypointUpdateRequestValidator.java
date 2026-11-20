package com.trek.admin.dto.request.requestValidator;

import com.trek.admin.dto.command.WayPointUpdateCommand;
import com.trek.admin.dto.request.v2.TrekWaypointUpdateRequest;
import com.trek.core.admin.service.MediaService;
import com.trek.core.admin.service.RouteWaypointQueryService;
import com.trek.core.entity.TrekWaypoint;
import com.trek.core.util.RequestValidator;
import com.trek.core.util.UpdateUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrekWaypointUpdateRequestValidator implements RequestValidator<TrekWaypointUpdateRequest, WayPointUpdateCommand> {

    private final RouteWaypointQueryService routeWaypointQueryService;
    private final MediaService mediaService;

    @Override
    public WayPointUpdateCommand validate(TrekWaypointUpdateRequest request) {

        TrekWaypoint trekWaypoint = routeWaypointQueryService.getById(request.getId());

        WayPointUpdateCommand command = new WayPointUpdateCommand();
        command.setTrekWaypoint(trekWaypoint);
        command.setSequenceOrder(UpdateUtil.getOrDefault(trekWaypoint.getSequenceOrder(), request.getSequenceOrder()));
        command.setName(UpdateUtil.getOrDefault(trekWaypoint.getName(), request.getName()));
        command.setSlug(request.getSlug());
        command.setLatitude(UpdateUtil.getOrDefault(trekWaypoint.getLatitude(), request.getLatitude()));
        command.setLatitudeNumeric(UpdateUtil.getOrDefault(trekWaypoint.getLatitudeNumeric(), request.getLatitudeNumeric()));
        command.setLongitude(UpdateUtil.getOrDefault(trekWaypoint.getLongitude(), request.getLongitude()));
        command.setLongitudeNumeric(UpdateUtil.getOrDefault(trekWaypoint.getLongitudeNumeric(), request.getLongitudeNumeric()));
        command.setAltitude(UpdateUtil.getOrDefault(trekWaypoint.getAltitude(), request.getAltitude()));
        command.setAltitudeNumeric(UpdateUtil.getOrDefault(trekWaypoint.getAltitudeNumeric(), request.getAltitudeNumeric()));
        command.setType(UpdateUtil.getOrDefault(trekWaypoint.getType(), request.getType()));
        command.setShortDescription(UpdateUtil.getOrDefault(trekWaypoint.getShortDescription(), request.getShortDescription()));
        command.setFullDescription(UpdateUtil.getOrDefault(trekWaypoint.getFullDescription(), request.getFullDescription()));
        command.setHasWifi(UpdateUtil.getOrDefault(trekWaypoint.getHasWifi(), request.getHasWifi()));
        command.setHasCharging(UpdateUtil.getOrDefault(trekWaypoint.getHasCharging(), request.getHasCharging()));
        command.setHasHotShower(UpdateUtil.getOrDefault(trekWaypoint.getHasHotShower(), request.getHasHotShower()));
        command.setHasAtm(UpdateUtil.getOrDefault(trekWaypoint.getHasAtm(), request.getHasAtm()));
        command.setAtmNotes(UpdateUtil.getOrDefault(trekWaypoint.getAtmNotes(), request.getAtmNotes()));
        command.setSignificance(UpdateUtil.getOrDefault(trekWaypoint.getSignificance(), request.getSignificance()));

        if (request.getImages() != null) {
            command.setImages(mediaService.finaAllByUrl(request.getImages()));
        }

        return command;
    }
}