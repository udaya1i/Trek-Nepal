package com.trek.admin.service;

import com.trek.admin.dto.command.TrekStrategyAddCommand;
import com.trek.admin.dto.command.TrekStrategyUpdateCommand;
import com.trek.core.admin.service.SlugAssignmentService;
import com.trek.core.constants.SluggableType;
import com.trek.core.entity.TrekStrategy;
import com.trek.core.repository.MediaRepository;
import com.trek.core.repository.TrekStrategyRepository;
import com.trek.core.config.ImageUploaderConfig;
import com.trek.core.dto.request.BulkImageRequest;
import com.trek.core.dto.response.CloudinaryResponse;
import com.trek.core.entity.Media;
import com.trek.core.exception.RequestValidationException;
import com.trek.core.util.ImageUploadUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TrekStrategyService {

    private final TrekStrategyRepository trekStrategyRepository;
    private final SlugAssignmentService slugAssignmentService;

    public TrekStrategy add(TrekStrategyAddCommand command) {
        TrekStrategy trekStrategy = new TrekStrategy();

        trekStrategy.setName(command.getName());
        trekStrategy.setDifficultyLevel(command.getDifficultyLevel());
        trekStrategy.setMaxAltitude(command.getMaxAltitude());
        trekStrategy.setTotalDistance(command.getTotalDistance());
        trekStrategy.setAboutTrek(command.getAboutTrek());
        trekStrategy.setHighlights(command.getHighlights());
        trekStrategy.setHighestPoint(command.getHighestPoint());
        trekStrategy.setLowestPoint(command.getLowestPoint());
        trekStrategy.setRouteType(command.getRouteType());
        trekStrategy.setAccessPointCity(command.getAccessPointCity());
        trekStrategy.setActualStartPoint(command.getActualStartPoint());
        trekStrategy.setActualEndPoint(command.getActualEndPoint());
        trekStrategy.setDestination(command.getDestination());
        trekStrategy.setTotalDays(command.getTotalDays());
        trekStrategy.setTotalDaysNumeric(command.getTotalDaysNumeric());
        trekStrategy.setTotalDistanceNumeric(command.getTotalDistanceNumeric());
        trekStrategy.setAcclimatizationDays(command.getAcclimatizationDays());
        trekStrategy.setDifficultyAdjustment(command.getDifficultyAdjustment());
        trekStrategy.setSlug(slugAssignmentService.ensureUniqueSlug(command.getName(), trekStrategyRepository::existsBySlug));

        return trekStrategyRepository.save(trekStrategy);
    }

    public TrekStrategy update(TrekStrategyUpdateCommand command) {
        TrekStrategy trekStrategy = command.getTrekStrategy();

        trekStrategy.setName(command.getName());
        trekStrategy.setDifficultyLevel(command.getDifficultyLevel());
        trekStrategy.setMaxAltitude(command.getMaxAltitude());
        trekStrategy.setTotalDistance(command.getTotalDistance());
        trekStrategy.setAboutTrek(command.getAboutTrek());
        trekStrategy.setShortDescription(command.getShortDescription());
        trekStrategy.setHighlights(command.getHighlights());
        trekStrategy.setHighestPoint(command.getHighestPoint());
        trekStrategy.setLowestPoint(command.getLowestPoint());
        trekStrategy.setRouteType(command.getRouteType());
        trekStrategy.setAccessPointCity(command.getAccessPointCity());
        trekStrategy.setActualStartPoint(command.getActualStartPoint());
        trekStrategy.setActualEndPoint(command.getActualEndPoint());
        trekStrategy.setTotalDays(command.getTotalDays());
        trekStrategy.setTotalDaysNumeric(command.getTotalDaysNumeric());
        trekStrategy.setTotalDistanceNumeric(command.getTotalDistanceNumeric());
        trekStrategy.setAcclimatizationDays(command.getAcclimatizationDays());
        trekStrategy.setDifficultyAdjustment(command.getDifficultyAdjustment());
        trekStrategy.setSlug(slugAssignmentService.resolveSlugOnUpdate(SluggableType.STRATEGY, trekStrategy.getId(),
                trekStrategy.getSlug(), command.getSlug(), trekStrategy.getName(), trekStrategyRepository::existsBySlug));

        return trekStrategyRepository.save(trekStrategy);
    }

}
