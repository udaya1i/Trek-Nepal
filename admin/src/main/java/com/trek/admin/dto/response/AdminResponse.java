package com.trek.admin.dto.response;

import com.trek.core.entity.Admin;
import lombok.Data;

import java.util.List;

@Data
public class AdminResponse {

    private Long id;
    private String name;
    private String username;
    private String email;
    private String avatar;
    private String isVerified;
    private boolean isSuperAdmin;
    private Long passwordAttemptCount;

    public static List<AdminResponse> of(List<Admin> admin) {
        return admin.stream().map(AdminResponse::of).toList();
    }

    public static AdminResponse of(Admin admin) {
        AdminResponse adminResponse = new AdminResponse();

        adminResponse.setId(admin.getId());
        adminResponse.setName(admin.getName());
        adminResponse.setUsername(admin.getUsername());
        adminResponse.setEmail(admin.getEmail());
        adminResponse.setAvatar(admin.getAvatar());
        adminResponse.setSuperAdmin(admin.isSuperAdmin());
        adminResponse.setPasswordAttemptCount(admin.getPasswordAttemptCount());

        return adminResponse;
    }
}
