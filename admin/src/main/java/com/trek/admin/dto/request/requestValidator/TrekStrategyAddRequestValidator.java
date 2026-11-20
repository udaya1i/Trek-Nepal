package com.trek.admin.dto.request.requestValidator;

import com.trek.admin.dto.command.TrekStrategyAddCommand;
import com.trek.admin.dto.request.v2.TrekStrategyAddRequest;
import com.trek.core.entity.Destination;
import com.trek.core.admin.service.DestinationQueryService;
import com.trek.core.util.RequestValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrekStrategyAddRequestValidator implements RequestValidator<TrekStrategyAddRequest, TrekStrategyAddCommand> {

    private final DestinationQueryService destinationQueryService;

    @Override
    public TrekStrategyAddCommand validate(TrekStrategyAddRequest request) {
        Destination destination = destinationQueryService.getById(request.getDestinationId());
        TrekStrategyAddCommand command = new TrekStrategyAddCommand();

        command.setTotalDays(request.getTotalDays());
        command.setTotalDaysNumeric(request.getTotalDaysNumeric());
        command.setAcclimatizationDays(request.getAcclimatizationDays());
        command.setDifficultyAdjustment(request.getDifficultyAdjustment());
        command.setName(request.getName());
        command.setDifficultyLevel(request.getDifficultyLevel());
        command.setMaxAltitude(request.getMaxAltitude());
        command.setTotalDistance(request.getTotalDistance());
        command.setTotalDistanceNumeric(request.getTotalDistanceNumeric());
        command.setAboutTrek(request.getAboutTrek());
        command.setHighlights(request.getHighlights());
        command.setHighestPoint(request.getHighestPoint());
        command.setLowestPoint(request.getLowestPoint());
        command.setRouteType(request.getRouteType());
        command.setAccessPointCity(request.getAccessPointCity());
        command.setActualStartPoint(request.getActualStartPoint());
        command.setActualEndPoint(request.getActualEndPoint());
        command.setDestination(destination);

        return command;
    }

}
