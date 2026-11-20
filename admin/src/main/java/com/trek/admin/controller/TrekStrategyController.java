package com.trek.admin.controller;

import com.trek.admin.dto.request.v2.TrekStrategyAddRequest;
import com.trek.admin.dto.request.v2.TrekStrategyUpdateRequest;
import com.trek.admin.dto.response.TrekStrategyAddResponse;
import com.trek.admin.facade.TrekStrategyFacade;
import com.trek.core.constants.AdminPathConstant;
import com.trek.core.dto.response.ServerResponse;
import com.trek.core.dto.response.TrekStrategyDetailResponse;
import com.trek.core.dto.response.TrekStrategyResponse;
import com.trek.core.security.constant.Authorities;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(AdminPathConstant.BASE_URL+ AdminPathConstant.TREK)
public class TrekStrategyController {

    private final TrekStrategyFacade trekStrategyFacade;

    @PostMapping
    @PreAuthorize(Authorities.DESTINATION_MANAGEMENT)
    public ResponseEntity<ServerResponse<TrekStrategyAddResponse>> add(@RequestBody @Valid TrekStrategyAddRequest request) {
        ServerResponse<TrekStrategyAddResponse> serverResponse = trekStrategyFacade.add(request);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PostMapping(AdminPathConstant.UPDATE)
    @PreAuthorize(Authorities.DESTINATION_MANAGEMENT)
    public ResponseEntity<ServerResponse<TrekStrategyAddResponse>> update(@RequestBody @Valid TrekStrategyUpdateRequest request) {
        ServerResponse<TrekStrategyAddResponse> serverResponse = trekStrategyFacade.update(request);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @GetMapping(AdminPathConstant.ID + AdminPathConstant.DETAIL)
    @PreAuthorize(Authorities.DESTINATION_MANAGEMENT)
    public ResponseEntity<ServerResponse<TrekStrategyDetailResponse>> getById(@PathVariable Long id) {
        ServerResponse<TrekStrategyDetailResponse> serverResponse = trekStrategyFacade.getById(id);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @GetMapping(AdminPathConstant.ID + AdminPathConstant.DESTINATION)
    @PreAuthorize(Authorities.DESTINATION_MANAGEMENT)
    public ResponseEntity<ServerResponse<List<TrekStrategyResponse>>> getByDestination(@PathVariable Long id) {
        ServerResponse<List<TrekStrategyResponse>> serverResponse = trekStrategyFacade.getByDestination(id);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

}
