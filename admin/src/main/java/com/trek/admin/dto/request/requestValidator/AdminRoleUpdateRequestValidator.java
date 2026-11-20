package com.trek.admin.dto.request.requestValidator;

import com.trek.admin.dto.command.AdminRoleUpdateCommand;
import com.trek.admin.dto.request.AdminRoleUpdateRequest;
import com.trek.core.entity.Role;
import com.trek.core.entity.Authority;
import com.trek.core.admin.service.AdminAuthoritiesQueryService;
import com.trek.core.admin.service.AdminRoleQueryService;
import com.trek.core.exception.RequestValidationException;
import com.trek.core.util.RequestValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author udaya
 */
@Service
@AllArgsConstructor
public class AdminRoleUpdateRequestValidator implements RequestValidator<AdminRoleUpdateRequest, AdminRoleUpdateCommand> {

    private final AdminRoleQueryService adminRoleQueryService;
    private final AdminAuthoritiesQueryService authoritiesQueryService;

    @Override
    public AdminRoleUpdateCommand validate(AdminRoleUpdateRequest request) {
        Role role = adminRoleQueryService.getById(request.getId());
        RequestValidator.Validation.of(adminRoleQueryService.existByName(request.getName()))
                .onSuccess(() -> new RequestValidationException("Role with name " + request.getName() + " already exist"));
        List<Authority> authorities = request.getUpdatedIds().stream().map(authoritiesQueryService::getById).toList();
        AdminRoleUpdateCommand command = new AdminRoleUpdateCommand();
        command.setRole(role);
        command.setDescription(request.getDescription());
        command.setName(request.getName());
        command.setAuthorities(authorities);
        return command;
    }
}
