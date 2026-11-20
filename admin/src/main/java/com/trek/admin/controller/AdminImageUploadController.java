package com.trek.admin.controller;

import com.trek.core.dto.response.ImageUploadResponse;
import com.trek.admin.facade.AdminImageUploadFacade;
import com.trek.core.constants.AdminPathConstant;
import com.trek.core.dto.request.ImageDeleteRequest;
import com.trek.core.dto.request.ImageUpdateRequest;
import com.trek.core.dto.request.ImageUploadRequest;
import com.trek.core.dto.response.ServerResponse;
import com.trek.core.security.constant.Authorities;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@AllArgsConstructor
@RequestMapping(AdminPathConstant.BASE_URL + AdminPathConstant.IMAGE)
public class AdminImageUploadController {

    private final AdminImageUploadFacade adminImageUploadFacade;

    @PostMapping(AdminPathConstant.UPLOAD)
    @PreAuthorize(Authorities.DESTINATION_MANAGEMENT)
    public ResponseEntity<ServerResponse<ImageUploadResponse>> upload(@ModelAttribute ImageUploadRequest request) {
        ServerResponse<ImageUploadResponse> serverResponse = adminImageUploadFacade.upload(request);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PreAuthorize(Authorities.DESTINATION_MANAGEMENT)
    @PostMapping(AdminPathConstant.DELETE)
    public ResponseEntity<ServerResponse<Void>> delete(@RequestBody @Valid ImageDeleteRequest request) {
        ServerResponse<Void> serverResponse = adminImageUploadFacade.delete(request);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PreAuthorize(Authorities.DESTINATION_MANAGEMENT)
    @PostMapping(AdminPathConstant.EDIT)
    public ResponseEntity<ServerResponse<Void>> edit(@RequestBody @Valid ImageUpdateRequest request) {
        ServerResponse<Void> serverResponse = adminImageUploadFacade.edit(request);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

}
