package com.trek.admin.dto.command;

import com.trek.core.entity.Role;
import com.trek.core.dto.command.Command;
import lombok.Data;

@Data
public class AdminCreateCommand implements Command {

    private String name;
    private String username;
    private String email;
    private String avatar;
    private boolean isSuperAdmin;
    private String password;
    private String superPassword;
    private Role role;

}

