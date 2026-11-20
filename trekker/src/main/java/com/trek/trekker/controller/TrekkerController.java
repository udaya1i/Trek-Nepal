package com.trek.trekker.controller;

import com.trek.core.constants.PublicPathConstant;
import com.trek.core.dto.request.ImageUploadRequest;
import com.trek.core.dto.response.ServerResponse;
import com.trek.core.security.constant.Authorities;
import com.trek.trekker.dto.request.TrekkerCreateRequest;
import com.trek.trekker.dto.request.TrekkerDeleteRequest;
import com.trek.trekker.dto.request.TrekkerEditRequest;
import com.trek.trekker.dto.response.TrekkerDetailResponse;
import com.trek.trekker.facade.TrekkerFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping(PublicPathConstant.BASE_URL + PublicPathConstant.TREKKERS)
@AllArgsConstructor
public class TrekkerController {

    private final TrekkerFacade trekkerFacade;

    @GetMapping
    public ResponseEntity<ServerResponse<List<TrekkerDetailResponse>>> getAll() {
        ServerResponse<List<TrekkerDetailResponse>> serverResponse = trekkerFacade.getAll();
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @GetMapping(PublicPathConstant.ID + PublicPathConstant.DETAIL)
    public ResponseEntity<ServerResponse<TrekkerDetailResponse>> getById(@PathVariable Long id) {
        ServerResponse<TrekkerDetailResponse> serverResponse = trekkerFacade.getById(id);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PostMapping(PublicPathConstant.CREATE)
    public ResponseEntity<ServerResponse<TrekkerDetailResponse>> create(@RequestBody @Valid TrekkerCreateRequest request) {
        ServerResponse<TrekkerDetailResponse> serverResponse = trekkerFacade.create(request);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PreAuthorize(Authorities.TREKKER_PROFILE)
    @PatchMapping(PublicPathConstant.ID + PublicPathConstant.EDIT)
    public ResponseEntity<ServerResponse<TrekkerDetailResponse>> edit(@PathVariable Long id, @RequestBody @Valid TrekkerEditRequest request) {
        ServerResponse<TrekkerDetailResponse> serverResponse = trekkerFacade.edit(id, request);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PreAuthorize(Authorities.TREKKER_PROFILE)
    @DeleteMapping(PublicPathConstant.ID + PublicPathConstant.DELETE)
    public ResponseEntity<ServerResponse<Void>> delete(@PathVariable Long id, @RequestBody @Valid TrekkerDeleteRequest request) {
        ServerResponse<Void> serverResponse = trekkerFacade.delete(id, request);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PreAuthorize(Authorities.TREKKER_PROFILE)
    @PostMapping(PublicPathConstant.UPLOAD + PublicPathConstant.PROFILE)
    public ResponseEntity<ServerResponse<Void>> uploadProfile(@ModelAttribute @Valid ImageUploadRequest request) {
        ServerResponse<Void> authenticate = trekkerFacade.uploadProfile(request);
        return ResponseEntity.status(authenticate.getHttpStatus()).body(authenticate);
    }

    @PreAuthorize(Authorities.TREKKER_PROFILE)
    @PostMapping(PublicPathConstant.UPLOAD + PublicPathConstant.COVER_PHOTO)
    public ResponseEntity<ServerResponse<Void>> uploadCoverPhoto(@ModelAttribute @Valid ImageUploadRequest request) {
        ServerResponse<Void> authenticate = trekkerFacade.uploadCover(request);
        return ResponseEntity.status(authenticate.getHttpStatus()).body(authenticate);
    }

}
