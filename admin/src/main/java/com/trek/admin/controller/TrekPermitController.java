package com.trek.admin.controller;

import com.trek.admin.dto.request.TrekPermitAddRequest;
import com.trek.admin.dto.request.v2.TrekPermitUpdateRequest;
import com.trek.admin.facade.TrekPermitFacade;
import com.trek.core.constants.AdminPathConstant;
import com.trek.core.dto.response.DestinationPermitResponse;
import com.trek.core.dto.response.ServerResponse;
import com.trek.core.security.constant.Authorities;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping(AdminPathConstant.BASE_URL + AdminPathConstant.TREK_PERMITS)
public class TrekPermitController {

    private final TrekPermitFacade trekPermitFacade;

    @PostMapping
    @PreAuthorize(Authorities.DESTINATION_MANAGEMENT)
    public ResponseEntity<ServerResponse<Void>> add(@RequestBody @Valid TrekPermitAddRequest request) {
        ServerResponse<Void> serverResponse = trekPermitFacade.add(request);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PostMapping(AdminPathConstant.UPDATE)
    @PreAuthorize(Authorities.DESTINATION_MANAGEMENT)
    public ResponseEntity<ServerResponse<Void>> update(@RequestBody @Valid TrekPermitUpdateRequest request) {
        ServerResponse<Void> serverResponse = trekPermitFacade.update(request);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @GetMapping(AdminPathConstant.ID)
    @PreAuthorize(Authorities.DESTINATION_MANAGEMENT)
    public ResponseEntity<ServerResponse<List<DestinationPermitResponse>>> getByTrek(@PathVariable("id") Long trekId) {
        ServerResponse<List<DestinationPermitResponse>> serverResponse = trekPermitFacade.getByTrek(trekId);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

}
