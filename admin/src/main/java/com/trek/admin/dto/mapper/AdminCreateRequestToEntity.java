package com.trek.admin.dto.mapper;

import com.trek.admin.dto.command.AdminCreateCommand;
import com.trek.core.entity.Admin;
import com.trek.core.constants.AdminStatus;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class AdminCreateRequestToEntity {

    public static Admin of(AdminCreateCommand command) {
        Admin admin = new Admin();
        admin.setRole(command.getRole());
        admin.setName(command.getName());
        admin.setUsername(command.getUsername());
        admin.setEmail(command.getEmail());
        admin.setStatus(AdminStatus.ACTIVE.getId());
        admin.setAvatar(command.getAvatar());
        admin.setSuperAdmin(command.isSuperAdmin());
         admin.setSuperPassword(command.getSuperPassword());
        admin.setIsVerified(false);
        admin.setOldPassword(null);
        admin.setPasswordChangedDate(LocalDateTime.now());
        admin.setPasswordAttemptCount(0L);

        return admin;
    }
}
