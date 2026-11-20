package com.trek.admin.dto.request.requestValidator;

import com.trek.admin.dto.command.AdminEditCommand;
import com.trek.admin.dto.request.AdminEditRequest;
import com.trek.core.entity.Admin;
import com.trek.core.admin.service.AdminQueryService;
import com.trek.core.util.RequestValidator;
import com.trek.core.util.UpdateUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminEditRequestValidator implements RequestValidator<AdminEditRequest, AdminEditCommand> {

    private final AdminQueryService adminQueryService;

    @Override
    public AdminEditCommand validate(AdminEditRequest request) {
        Admin admin = adminQueryService.getById(request.getId());

        AdminEditCommand command = new AdminEditCommand();
        command.setAdmin(admin);
        command.setName(UpdateUtil.getOrDefault(admin.getName(), request.getName()));
        command.setAvatar(UpdateUtil.getOrDefault(admin.getAvatar(), request.getAvatar()));
        command.setEmail(UpdateUtil.getOrDefault(admin.getEmail(), request.getEmail()));
        return command;
    }

}
