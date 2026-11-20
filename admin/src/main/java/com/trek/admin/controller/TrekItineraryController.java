package com.trek.admin.controller;


import com.trek.admin.dto.request.TrekItineraryRequest;
import com.trek.admin.dto.request.v2.TrekItineraryUpdateRequest;
import com.trek.admin.facade.TrekItineraryFacade;
import com.trek.core.constants.AdminPathConstant;
import com.trek.core.dto.response.ServerResponse;
import com.trek.core.dto.response.TrekItineraryDetailResponse;
import com.trek.core.security.constant.Authorities;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping(AdminPathConstant.BASE_URL + AdminPathConstant.ITINERARIES)
public class TrekItineraryController {

    private final TrekItineraryFacade facade;

    @PostMapping
    @PreAuthorize(Authorities.DESTINATION_MANAGEMENT)
    public ResponseEntity<ServerResponse<TrekItineraryDetailResponse>> addItinerary(@RequestBody @Valid TrekItineraryRequest request) {
        ServerResponse<TrekItineraryDetailResponse> serverResponse = facade.addItinerary(request);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PostMapping(AdminPathConstant.UPDATE)
    @PreAuthorize(Authorities.DESTINATION_MANAGEMENT)
    public ResponseEntity<ServerResponse<TrekItineraryDetailResponse>> updateItinerary(@RequestBody @Valid TrekItineraryUpdateRequest request) {
        ServerResponse<TrekItineraryDetailResponse> serverResponse = facade.updateItinerary(request);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @GetMapping(AdminPathConstant.ID)
    @PreAuthorize(Authorities.DESTINATION_MANAGEMENT)
    public ResponseEntity<ServerResponse<TrekItineraryDetailResponse>> getByTrekId(@PathVariable("id") Long trekId) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getByTrek(trekId));
    }

    @GetMapping(AdminPathConstant.ID + AdminPathConstant.STRATEGY)
    @PreAuthorize(Authorities.DESTINATION_MANAGEMENT)
    public ResponseEntity<ServerResponse<List<TrekItineraryDetailResponse>>> getByStrategy(@PathVariable("id") Long strategyId) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getAll(strategyId));
    }
}
