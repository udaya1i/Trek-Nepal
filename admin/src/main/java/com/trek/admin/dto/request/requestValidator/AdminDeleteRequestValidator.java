package com.trek.admin.dto.request.requestValidator;

import com.trek.admin.dto.command.AdminDeleteCommand;
import com.trek.admin.dto.request.AdminDeleteRequest;
import com.trek.core.repository.AdminRepository;
import com.trek.core.util.RequestValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminDeleteRequestValidator implements RequestValidator<AdminDeleteRequest, AdminDeleteCommand> {

    private AdminRepository adminRepository;
    @Override
    public AdminDeleteCommand validate(AdminDeleteRequest request) {
         return new AdminDeleteCommand();
    }
}
