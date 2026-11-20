package com.trek.admin.dto.request.requestValidator;

import com.trek.admin.dto.command.AdminCreateCommand;
import com.trek.admin.dto.request.AdminCreateRequest;
import com.trek.core.entity.Role;
import com.trek.core.admin.service.AdminQueryService;
import com.trek.core.admin.service.AdminRoleQueryService;
import com.trek.core.exception.RequestValidationException;
import com.trek.core.util.RequestValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminCreateRequestValidator implements RequestValidator<AdminCreateRequest, AdminCreateCommand> {

    private final AdminQueryService adminQueryService;
    private final AdminRoleQueryService adminRoleQueryService;

    @Override
    public AdminCreateCommand validate(AdminCreateRequest request) {
        adminQueryService.getByUsername(request.getUsername()).ifPresent(admin -> {
                    throw new RequestValidationException("Username already exist");
                }
        );
        Role role = adminRoleQueryService.getById(request.getAdminRole());
        AdminCreateCommand command = new AdminCreateCommand();
        command.setAvatar(request.getAvatar());
        command.setName(request.getName());
        command.setEmail(request.getEmail());
        command.setSuperAdmin(request.isSuperAdmin());
        command.setPassword(request.getPassword());
        command.setUsername(request.getUsername());
        command.setRole(role);
        return command;
    }
}
