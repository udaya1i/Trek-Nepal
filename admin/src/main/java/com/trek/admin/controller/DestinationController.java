package com.trek.admin.controller;

import com.trek.admin.dto.request.v2.DestinationAddRequest;
import com.trek.admin.dto.request.v2.DestinationPublishRequest;
import com.trek.admin.dto.request.v2.DestinationUpdateRequest;
import com.trek.admin.dto.response.DestinationAddResponse;
import com.trek.admin.facade.DestinationFacade;
import com.trek.core.constants.AdminPathConstant;
import com.trek.core.dto.request.FilterRequest;
import com.trek.core.dto.response.DestinationDetailResponse;
import com.trek.core.dto.response.DestinationResponse;
import com.trek.core.dto.response.PaginatedResponse;
import com.trek.core.dto.response.ServerResponse;
import com.trek.core.security.constant.Authorities;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(AdminPathConstant.BASE_URL + AdminPathConstant.DESTINATIONS)
public class DestinationController {

    private final DestinationFacade destinationFacade;

    @PostMapping(AdminPathConstant.CREATE)
    @PreAuthorize(Authorities.DESTINATION_MANAGEMENT)
    public ResponseEntity<ServerResponse<DestinationAddResponse>> create(@Valid @RequestBody DestinationAddRequest request) {
        ServerResponse<DestinationAddResponse> serverResponse = destinationFacade.create(request);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @GetMapping(AdminPathConstant.ID)
    @PreAuthorize(Authorities.DESTINATION_MANAGEMENT)
    public ResponseEntity<ServerResponse<DestinationDetailResponse>> getById(@PathVariable Long id) {
        ServerResponse<DestinationDetailResponse> serverResponse = destinationFacade.getById(id);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PostMapping
    @PreAuthorize(Authorities.DESTINATION_MANAGEMENT)
    public ResponseEntity<PaginatedResponse<DestinationResponse>> getAll(@RequestBody FilterRequest request) {
        PaginatedResponse<DestinationResponse> paginatedResponse = destinationFacade.getAll(request);
        return ResponseEntity.status(paginatedResponse.getStatus()).body(paginatedResponse);
    }

    @PreAuthorize(Authorities.DESTINATION_MANAGEMENT)
    @PostMapping(AdminPathConstant.UPDATE)
    public ResponseEntity<ServerResponse<DestinationAddResponse>> update(@Valid @RequestBody DestinationUpdateRequest request) {
        ServerResponse<DestinationAddResponse> serverResponse = destinationFacade.update(request);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PreAuthorize(Authorities.DESTINATION_MANAGEMENT)
    @PostMapping(AdminPathConstant.PUBLISH)
    public ResponseEntity<ServerResponse<DestinationAddResponse>> publish(@Valid @RequestBody DestinationPublishRequest request) {
        ServerResponse<DestinationAddResponse> serverResponse = destinationFacade.publish(request);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }
}
