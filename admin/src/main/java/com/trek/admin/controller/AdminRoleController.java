package com.trek.admin.controller;

import com.trek.admin.dto.request.AdminRoleCreateRequest;
import com.trek.admin.dto.request.AdminRoleUpdateRequest;
import com.trek.admin.dto.response.AdminRoleResponse;
import com.trek.admin.facade.AdminRoleFacade;
import com.trek.core.constants.AdminPathConstant;
import com.trek.core.dto.response.ServerResponse;
import com.trek.core.security.constant.Authorities;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping(AdminPathConstant.BASE_URL + AdminPathConstant.ROLES)
@AllArgsConstructor
public class AdminRoleController {

    private final AdminRoleFacade facade;

    @PreAuthorize(Authorities.ROLE_MANAGEMENT)
    @PostMapping(AdminPathConstant.CREATE)
    public ResponseEntity<ServerResponse<Void>> create(@RequestBody @Valid AdminRoleCreateRequest request) {
        ServerResponse<Void> serverResponse = facade.create(request);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PreAuthorize(Authorities.ROLE_MANAGEMENT)
    @PostMapping(AdminPathConstant.EDIT)
    public ResponseEntity<ServerResponse<Void>> edit(@RequestBody @Valid AdminRoleUpdateRequest request) {
        ServerResponse<Void> serverResponse = facade.update(request);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PreAuthorize(Authorities.ROLE_MANAGEMENT)
    @GetMapping(AdminPathConstant.ID)
    public ResponseEntity<ServerResponse<AdminRoleResponse>> getById(@PathVariable Long id) {
        ServerResponse<AdminRoleResponse> serverResponse = facade.getById(id);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PreAuthorize(Authorities.ROLE_MANAGEMENT)
    @GetMapping
    public ResponseEntity<ServerResponse<List<AdminRoleResponse>>> getAll() {
        ServerResponse<List<AdminRoleResponse>> serverResponse = facade.getAll();
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

}

