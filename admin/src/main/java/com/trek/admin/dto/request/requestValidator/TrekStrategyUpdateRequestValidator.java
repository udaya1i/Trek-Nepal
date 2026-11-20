package com.trek.admin.dto.request.requestValidator;

import com.trek.admin.dto.command.TrekStrategyUpdateCommand;
import com.trek.admin.dto.request.v2.TrekStrategyUpdateRequest;
import com.trek.core.admin.service.TrekStrategyQueryService;
import com.trek.core.entity.TrekStrategy;
import com.trek.core.util.RequestValidator;
import com.trek.core.util.UpdateUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrekStrategyUpdateRequestValidator implements RequestValidator<TrekStrategyUpdateRequest, TrekStrategyUpdateCommand> {

    private final TrekStrategyQueryService trekStrategyQueryService;

    @Override
    public TrekStrategyUpdateCommand validate(TrekStrategyUpdateRequest request) {

        TrekStrategy trekStrategy = trekStrategyQueryService.findById(request.getId());

        TrekStrategyUpdateCommand command = new TrekStrategyUpdateCommand();
        command.setTrekStrategy(trekStrategy);
        command.setName(UpdateUtil.getOrDefault(trekStrategy.getName(), request.getName()));
        command.setSlug(request.getSlug());
        command.setTotalDays(UpdateUtil.getOrDefault(trekStrategy.getTotalDays(), request.getTotalDays()));
        command.setTotalDaysNumeric(UpdateUtil.getOrDefault(trekStrategy.getTotalDaysNumeric(), request.getTotalDaysNumeric()));
        command.setAcclimatizationDays(UpdateUtil.getOrDefault(trekStrategy.getAcclimatizationDays(), request.getAcclimatizationDays()));
        command.setDifficultyAdjustment(UpdateUtil.getOrDefault(trekStrategy.getDifficultyAdjustment(), request.getDifficultyAdjustment()));
        command.setMaxAltitude(UpdateUtil.getOrDefault(trekStrategy.getMaxAltitude(), request.getMaxAltitude()));
        command.setTotalDistance(UpdateUtil.getOrDefault(trekStrategy.getTotalDistance(), request.getTotalDistance()));
        command.setTotalDistanceNumeric(UpdateUtil.getOrDefault(trekStrategy.getTotalDistanceNumeric(), request.getTotalDistanceNumeric()));
        command.setAboutTrek(UpdateUtil.getOrDefault(trekStrategy.getAboutTrek(), request.getAboutTrek()));
        command.setShortDescription(UpdateUtil.getOrDefault(trekStrategy.getShortDescription(), request.getShortDescription()));
        command.setHighestPoint(UpdateUtil.getOrDefault(trekStrategy.getHighestPoint(), request.getHighestPoint()));
        command.setLowestPoint(UpdateUtil.getOrDefault(trekStrategy.getLowestPoint(), request.getLowestPoint()));
        command.setAccessPointCity(UpdateUtil.getOrDefault(trekStrategy.getAccessPointCity(), request.getAccessPointCity()));
        command.setActualStartPoint(UpdateUtil.getOrDefault(trekStrategy.getActualStartPoint(), request.getActualStartPoint()));
        command.setActualEndPoint(UpdateUtil.getOrDefault(trekStrategy.getActualEndPoint(), request.getActualEndPoint()));
        command.setRouteType(UpdateUtil.getOrDefault(trekStrategy.getRouteType(), request.getRouteType()));
        command.setDifficultyLevel(UpdateUtil.getOrDefault(trekStrategy.getDifficultyLevel(), request.getDifficultyLevel()));
        command.setHighlights(UpdateUtil.getOrDefault(trekStrategy.getHighlights(), request.getHighlights()));

        return command;
    }
}