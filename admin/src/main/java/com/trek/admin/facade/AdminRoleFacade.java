package com.trek.admin.facade;

import com.trek.admin.dto.command.AdminRoleCreateCommand;
import com.trek.admin.dto.command.AdminRoleUpdateCommand;
import com.trek.admin.dto.request.AdminRoleCreateRequest;
import com.trek.admin.dto.request.AdminRoleUpdateRequest;
import com.trek.admin.dto.request.requestValidator.AdminRoleCreateRequestValidator;
import com.trek.admin.dto.request.requestValidator.AdminRoleUpdateRequestValidator;
import com.trek.admin.dto.response.AdminRoleResponse;
import com.trek.admin.service.AdminRoleService;
import com.trek.core.admin.service.AdminRoleQueryService;
import com.trek.core.dto.response.ServerResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminRoleFacade {

    private final AdminRoleService adminRoleService;
    private final AdminRoleQueryService adminRoleQueryService;
    private final AdminRoleCreateRequestValidator createRequestValidator;
    private final AdminRoleUpdateRequestValidator updateRequestValidator;

    public ServerResponse<Void> create(AdminRoleCreateRequest request) {
        AdminRoleCreateCommand command = createRequestValidator.validate(request);
        adminRoleService.create(command);
        return ServerResponse.<Void>builder()
                .message("Admin role created successfully")
                .status(HttpStatus.CREATED)
                .build();
    }

    public ServerResponse<Void> update(AdminRoleUpdateRequest request) {
        AdminRoleUpdateCommand validate = updateRequestValidator.validate(request);
        adminRoleService.update(validate);
        return ServerResponse.<Void>builder()
                .message("Role updated successfully")
                .status(HttpStatus.OK)
                .build();
    }

    public ServerResponse<List<AdminRoleResponse>> getAll() {
        return ServerResponse.<List<AdminRoleResponse>>builder().message("Role(s) fetched successfully")
                .data(AdminRoleResponse.of(adminRoleQueryService.getAll()))
                .status(HttpStatus.OK)
                .build();
    }

    public ServerResponse<AdminRoleResponse> getById(Long id) {
        return ServerResponse.<AdminRoleResponse>builder().message("Role detail fetched successfully")
                .data(AdminRoleResponse.of(adminRoleQueryService.getById(id)))
                .status(HttpStatus.OK)
                .build();
    }

}
