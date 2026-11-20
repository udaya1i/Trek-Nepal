package com.trek.admin.dto.command;

import com.trek.core.entity.Role;
import com.trek.core.entity.Authority;
import com.trek.core.dto.command.Command;
import lombok.Data;

import java.util.List;

@Data
public class AdminRoleUpdateCommand implements Command {

    private Role role;
    private String name;
    private String description;
    private List<Authority> authorities;
}
