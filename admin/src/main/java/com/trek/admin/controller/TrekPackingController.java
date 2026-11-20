package com.trek.admin.controller;

import com.trek.admin.dto.request.TrekPackagingAddRequest;
import com.trek.admin.dto.request.v2.PackageUpdateRequest;
import com.trek.admin.facade.TrekPackagingFacade;
import com.trek.core.constants.AdminPathConstant;
import com.trek.core.dto.response.ServerResponse;
import com.trek.core.dto.response.TrekPackagingResponse;
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
@RequestMapping(AdminPathConstant.BASE_URL + AdminPathConstant.TREK_PACKAGINGS)
public class TrekPackingController {

    private final TrekPackagingFacade facade;

    @PostMapping
    @PreAuthorize(Authorities.DESTINATION_MANAGEMENT)
    public ResponseEntity<ServerResponse<Void>> add(@RequestBody @Valid TrekPackagingAddRequest request) {
        ServerResponse<Void> serverResponse = facade.add(request);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PostMapping(AdminPathConstant.UPDATE)
    @PreAuthorize(Authorities.DESTINATION_MANAGEMENT)
    public ResponseEntity<ServerResponse<Void>> update(@RequestBody @Valid PackageUpdateRequest request) {
        ServerResponse<Void> serverResponse = facade.update(request);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @GetMapping(AdminPathConstant.ID)
    @PreAuthorize(Authorities.DESTINATION_MANAGEMENT)
    public ResponseEntity<ServerResponse<List<TrekPackagingResponse>>> getByTrek(@PathVariable("id") Long trekId) {
        ServerResponse<List<TrekPackagingResponse>> serverResponse = facade.getByTrek(trekId);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }


}
