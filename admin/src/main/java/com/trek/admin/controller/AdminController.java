package com.trek.admin.controller;

import com.trek.admin.dto.request.AdminCreateRequest;
import com.trek.admin.dto.request.AdminDeleteRequest;
import com.trek.admin.dto.request.AdminEditRequest;
import com.trek.admin.dto.response.AdminResponse;
import com.trek.admin.facade.AdminFacade;
import com.trek.core.constants.AdminPathConstant;
import com.trek.core.dto.response.ServerResponse;
import com.trek.core.security.constant.Authorities;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping(AdminPathConstant.BASE_URL + AdminPathConstant.ADMIN)
@AllArgsConstructor
public class AdminController {

    private final AdminFacade adminFacade;

    @GetMapping
    @PreAuthorize(Authorities.ADMIN_MANAGEMENT)
    public ResponseEntity<ServerResponse<List<AdminResponse>>> getAll() {
        ServerResponse<List<AdminResponse>> serverResponse = adminFacade.getAll();
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @GetMapping(AdminPathConstant.ID)
    @PreAuthorize(Authorities.ADMIN_MANAGEMENT)
    public ResponseEntity<ServerResponse<AdminResponse>> getById(@PathVariable Long id) {
        ServerResponse<AdminResponse> serverResponse = adminFacade.getById(id);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PreAuthorize(Authorities.ADMIN_MANAGEMENT)
    @PostMapping
    public ResponseEntity<ServerResponse<AdminResponse>> create(@RequestBody @Valid AdminCreateRequest request) {
        ServerResponse<AdminResponse> serverResponse = adminFacade.create(request);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PreAuthorize(Authorities.ADMIN_MANAGEMENT)
    @PatchMapping(AdminPathConstant.ID)
    public ResponseEntity<ServerResponse<AdminResponse>> edit(
            @PathVariable Long id,
            @RequestBody @Valid AdminEditRequest request
    ) {
        ServerResponse<AdminResponse> serverResponse = adminFacade.edit(id, request);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PreAuthorize(Authorities.ADMIN_MANAGEMENT)
    @DeleteMapping(AdminPathConstant.ID)
    public ResponseEntity<ServerResponse<Void>> delete(
            @PathVariable Long id,
            @RequestBody @Valid AdminDeleteRequest request) {
        ServerResponse<Void> serverResponse = adminFacade.delete(id, request);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }
}
