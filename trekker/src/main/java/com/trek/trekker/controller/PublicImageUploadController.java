package com.trek.trekker.controller;


import com.trek.core.constants.PublicPathConstant;
import com.trek.core.dto.request.ImageDeleteRequest;
import com.trek.core.dto.request.ImageUpdateRequest;
import com.trek.core.dto.request.ImageUploadRequest;
import com.trek.core.dto.response.ServerResponse;
import com.trek.core.security.constant.Authorities;
import com.trek.trekker.facade.PublicImageUploadFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(PublicPathConstant.BASE_URL + PublicPathConstant.IMAGE)
public class PublicImageUploadController {

    private final PublicImageUploadFacade imageUploadFacade;

    @PostMapping(PublicPathConstant.UPLOAD)
//    @PreAuthorize(Authorities.TREKKER_STORY)
    public ResponseEntity<ServerResponse<com.trek.core.dto.response.ImageUploadResponse>> upload(@ModelAttribute ImageUploadRequest request) {
        ServerResponse<com.trek.core.dto.response.ImageUploadResponse> serverResponse = imageUploadFacade.upload(request);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PreAuthorize(Authorities.TREKKER_STORY)
    @PostMapping(PublicPathConstant.DELETE)
    public ResponseEntity<ServerResponse<Void>> delete(@RequestBody @Valid ImageDeleteRequest request) {
        ServerResponse<Void> serverResponse = imageUploadFacade.delete(request);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PreAuthorize(Authorities.TREKKER_STORY)
    @PostMapping(PublicPathConstant.EDIT)
    public ResponseEntity<ServerResponse<Void>> edit(@RequestBody @Valid ImageUpdateRequest request) {
        ServerResponse<Void> serverResponse = imageUploadFacade.edit(request);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

}


