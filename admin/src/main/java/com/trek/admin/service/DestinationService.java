package com.trek.admin.service;

import com.trek.admin.dto.command.DestinationAddCommand;
import com.trek.admin.dto.command.DestinationUpdateCommand;
import com.trek.core.admin.service.MediaMappingService;
import com.trek.core.admin.service.SlugAssignmentService;
import com.trek.core.admin.specification.DestinationSpecification;
import com.trek.core.constants.DestinationStatus;
import com.trek.core.constants.SluggableType;
import com.trek.core.dto.request.FilterRequest;
import com.trek.core.entity.Destination;
import com.trek.core.repository.DestinationRepository;
import com.trek.core.specification.FilterFieldToEntityFieldMapper;
import com.trek.core.util.PaginationUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class DestinationService {

    private final DestinationRepository destinationRepository;
    private final MediaMappingService mediaMappingService;
    private final SlugAssignmentService slugAssignmentService;

    @Transactional
    public Destination create(DestinationAddCommand command) {

        Destination destination = new Destination();
        destination.setName(command.getName());
        destination.setDestinationType(command.getDestinationType());
        destination.setTrailCategory(command.getTrailCategories());
        destination.setAltitude(command.getAltitude());
        destination.setAltitudeNumeric(command.getAltitudeNumeric());
        destination.setLatitude(command.getLatitude());
        destination.setLatitudeNumeric(command.getLatitudeNumeric());
        destination.setLongitude(command.getLongitude());
        destination.setLongitudeNumeric(command.getLongitudeNumeric());
        destination.setDescription(command.getDescription());
        destination.setHighlights(command.getHighlights());
        destination.setLocalLevel(command.getLocalLevel());
        destination.setDestinationStatus(DestinationStatus.PENDING);
        destination.setProvince(command.getProvince());
        destination.setRegion(command.getRegion());
        destination.setDistrict(command.getDistrict());
        destination.setPrimaryAccessCity(command.getPrimaryAccessCity());
        destination.setDistanceFromAccessCity(command.getDistanceFromAccessCity());
        destination.setSlug(slugAssignmentService.ensureUniqueSlug(command.getName(), destinationRepository::existsBySlug));
        Destination save = destinationRepository.save(destination);
        mediaMappingService.map(command.getImages(), save);
        return save;
    }

    @Transactional
    public Destination update(DestinationUpdateCommand command) {
        Destination destination = command.getDestination();
        destination.setName(command.getName());
        destination.setDestinationType(command.getDestinationType());
        destination.setTrailCategory(command.getTrailCategories());
        destination.setAltitude(command.getAltitude());
        destination.setAltitudeNumeric(command.getAltitudeNumeric());
        destination.setLatitude(command.getLatitude());
        destination.setLatitudeNumeric(command.getLatitudeNumeric());
        destination.setLongitude(command.getLongitude());
        destination.setLongitudeNumeric(command.getLongitudeNumeric());
        destination.setDescription(command.getDescription());
        destination.setHighlights(command.getHighlights());
        destination.setLocalLevel(command.getLocalLevel());
        destination.setDestinationStatus(DestinationStatus.PENDING);
        destination.setProvince(command.getProvince());
        destination.setRegion(command.getRegion());
        destination.setDistrict(command.getDistrict());
        destination.setPrimaryAccessCity(command.getPrimaryAccessCity());
        destination.setDistanceFromAccessCity(command.getDistanceFromAccessCity());
        destination.setSlug(slugAssignmentService.resolveSlugOnUpdate(SluggableType.DESTINATION, destination.getId(),
                destination.getSlug(), command.getSlug(), destination.getName(), destinationRepository::existsBySlug));
        mediaMappingService.map(command.getImages(), destination);
        return destinationRepository.save(destination);
    }

    @Transactional(readOnly = true)
    public Page<Destination> getAll(FilterRequest request) {
        return destinationRepository.findAll(DestinationSpecification.searchSpec(request.getSearch(), FilterFieldToEntityFieldMapper.map(request.getFilterCriteriaRequests(), Destination.class)), PaginationUtil.of(request.getPaginationRequest()));
    }


    @Transactional
    public void publish(Destination destination) {
        destination.setDestinationStatus(DestinationStatus.PUBLISHED);
        destinationRepository.save(destination);
    }
}
