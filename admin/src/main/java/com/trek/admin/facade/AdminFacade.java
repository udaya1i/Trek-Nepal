package com.trek.admin.facade;

import com.trek.admin.dto.command.AdminCreateCommand;
import com.trek.admin.dto.command.AdminEditCommand;
import com.trek.admin.dto.request.AdminCreateRequest;
import com.trek.admin.dto.request.AdminDeleteRequest;
import com.trek.admin.dto.request.AdminEditRequest;
import com.trek.admin.dto.request.requestValidator.AdminCreateRequestValidator;
import com.trek.admin.dto.request.requestValidator.AdminDeleteRequestValidator;
import com.trek.admin.dto.request.requestValidator.AdminEditRequestValidator;
import com.trek.admin.dto.response.AdminResponse;
import com.trek.admin.service.AdminService;
import com.trek.core.entity.Admin;
import com.trek.core.constants.AdminStatus;
import com.trek.core.dto.response.ServerResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminFacade {

    private final AdminService adminService;
    private final AdminCreateRequestValidator adminCreateRequestValidator;
    private final AdminEditRequestValidator adminEditRequestValidator;
    private final AdminDeleteRequestValidator adminDeleteRequestValidator;

    public ServerResponse<AdminResponse> create(AdminCreateRequest request) {
        AdminCreateCommand command = adminCreateRequestValidator.validate(request);
        Admin admin = adminService.create(command);
        return ServerResponse.<AdminResponse>builder()
                .data(AdminResponse.of(admin))
                .status(HttpStatus.CREATED)
                .message("Admin created successfully.")
                .build();
    }

    public ServerResponse<AdminResponse> edit(Long id, AdminEditRequest request) {
        AdminEditCommand command = adminEditRequestValidator.validate(request);
        Admin admin = adminService.edit(command);
        return ServerResponse.<AdminResponse>builder()
                .data(AdminResponse.of(admin))
                .status(HttpStatus.OK)
                .message("Admin edited successfully.")
                .build();
    }

    public ServerResponse<Void> delete(Long id, AdminDeleteRequest request) {
        Admin admin = adminService.getById(id);
        admin.setStatus(AdminStatus.DELETED.getId());
        admin.setRemark(request.getRemarks());
        adminService.delete(admin);
        return ServerResponse.<Void>builder()
                .status(HttpStatus.OK)
                .message("Admin deleted successfully.")
                .build();
    }

    public ServerResponse<List<AdminResponse>> getAll() {
        List<Admin> admin = adminService.getAll();
        return ServerResponse.<List<AdminResponse>>builder()
                .data(AdminResponse.of(admin))
                .status(HttpStatus.OK)
                .message("Admin(s) fetched successfully")
                .build();
    }

    public ServerResponse<AdminResponse> getById(Long id) {
        Admin admin = adminService.getById(id);
        return ServerResponse.<AdminResponse>builder()
                .data(AdminResponse.of(admin))
                .status(HttpStatus.OK)
                .message("Admin detail fetched successfully")
                .build();
    }


}
