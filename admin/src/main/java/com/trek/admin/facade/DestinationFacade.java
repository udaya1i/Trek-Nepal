package com.trek.admin.facade;

import com.trek.admin.dto.command.DestinationAddCommand;
import com.trek.admin.dto.command.DestinationUpdateCommand;
import com.trek.admin.dto.request.requestValidator.DestinationAddRequestValidator;
import com.trek.admin.dto.request.requestValidator.DestinationUpdateRequestValidator;
import com.trek.admin.dto.request.v2.DestinationAddRequest;
import com.trek.admin.dto.request.v2.DestinationPublishRequest;
import com.trek.admin.dto.request.v2.DestinationUpdateRequest;
import com.trek.admin.dto.response.DestinationAddResponse;
import com.trek.admin.service.DestinationService;
import com.trek.core.admin.service.DestinationQueryService;
import com.trek.core.admin.service.MediaMappingService;
import com.trek.core.constants.MediaOwnerType;
import com.trek.core.dto.request.FilterRequest;
import com.trek.core.dto.response.DestinationDetailResponse;
import com.trek.core.dto.response.DestinationResponse;
import com.trek.core.dto.response.PaginatedResponse;
import com.trek.core.dto.response.ServerResponse;
import com.trek.core.entity.BaseEntity;
import com.trek.core.entity.Destination;
import com.trek.core.entity.MediaMapping;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DestinationFacade {

    private final DestinationService destinationService;
    private final DestinationAddRequestValidator addRequestValidator;
    private final DestinationUpdateRequestValidator destinationUpdateRequestValidator;
    private final DestinationQueryService destinationQueryService;
    private final MediaMappingService mediaMappingService;

    public ServerResponse<DestinationAddResponse> create(DestinationAddRequest request) {
        DestinationAddCommand validate = addRequestValidator.validate(request);
        Destination destination = destinationService.create(validate);
        return ServerResponse.<DestinationAddResponse>builder()
                .message("Destination added successfully")
                .status(HttpStatus.CREATED)
                .data(new DestinationAddResponse(destination.getId()))
                .build();
    }

    public ServerResponse<DestinationAddResponse> update(DestinationUpdateRequest request) {
        DestinationUpdateCommand command = destinationUpdateRequestValidator.validate(request);
        Destination destination = destinationService.update(command);
        return ServerResponse.<DestinationAddResponse>builder()
                .message("Destination updated successfully")
                .status(HttpStatus.OK)
                .data(new DestinationAddResponse(destination.getId()))
                .build();
    }

    public ServerResponse<DestinationAddResponse> publish(DestinationPublishRequest request) {
        Destination destination = destinationQueryService.getById(request.getId());
        destinationService.publish(destination);
        return ServerResponse.<DestinationAddResponse>builder()
                .message("Destination published successfully")
                .status(HttpStatus.OK)
                .data(new DestinationAddResponse(destination.getId()))
                .build();
    }


    public ServerResponse<DestinationDetailResponse> getById(Long id) {
        Destination destination = destinationQueryService.getById(id);
        List<MediaMapping> allMedia = mediaMappingService.getAllImage(destination.getId(), MediaOwnerType.DESTINATION);
        return ServerResponse.<DestinationDetailResponse>builder()
                .message("Destination fetched successfully")
                .status(HttpStatus.OK)
                .data(DestinationDetailResponse.of(destination, allMedia))
                .build();
    }

    //TODO: Fetch image and detail using single query
    public PaginatedResponse<DestinationResponse> getAll(FilterRequest request) {
        Page<Destination> destinationPage = destinationService.getAll(request);

        List<Long> ids = destinationPage.getContent().stream().map(BaseEntity::getId).toList();

        Map<Long, List<MediaMapping>> collect = mediaMappingService.getAllImage(ids, MediaOwnerType.DESTINATION)
                .stream().collect(Collectors.groupingBy(MediaMapping::getOwnerId));

        return PaginatedResponse.data(destinationPage.map(destination ->
                        DestinationResponse.of(destination, collect.getOrDefault(destination.getId(), List.of()))))
                .message("Destination(s) fetched successfully")
                .status(HttpStatus.OK)
                .build();
    }


}
