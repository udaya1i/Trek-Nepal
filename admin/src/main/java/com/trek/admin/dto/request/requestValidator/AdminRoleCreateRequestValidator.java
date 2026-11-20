package com.trek.admin.dto.request.requestValidator;

import com.trek.admin.dto.command.AdminRoleCreateCommand;
import com.trek.admin.dto.request.AdminRoleCreateRequest;
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
public class AdminRoleCreateRequestValidator implements RequestValidator<AdminRoleCreateRequest, AdminRoleCreateCommand> {

    private final AdminRoleQueryService adminRoleQueryService;
    private final AdminAuthoritiesQueryService authoritiesQueryService;

    @Override
    public AdminRoleCreateCommand validate(AdminRoleCreateRequest request) {

        RequestValidator.Validation.of(adminRoleQueryService.existByName(request.getName()))
                .onSuccess(() -> new RequestValidationException("Role with name " + request.getName() + " already exist"));

        List<Authority> authorities = request.getAuthoritiesIds().stream()
                .map(authoritiesQueryService::getById)
                .toList();

        AdminRoleCreateCommand command = new AdminRoleCreateCommand();
        command.setName(request.getName());
        command.setDescription(request.getDescription());
        command.setAuthorities(authorities);

        return command;
    }
}
