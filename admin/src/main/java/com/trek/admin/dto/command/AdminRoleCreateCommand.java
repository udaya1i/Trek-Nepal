package com.trek.admin.dto.command;

import com.trek.core.entity.Authority;
import com.trek.core.dto.command.Command;
import lombok.Data;

import java.util.List;

@Data
public class AdminRoleCreateCommand implements Command {
    private String name;
    private String description;
    private List<Authority> authorities;
}
