package com.trek.admin.dto.command;

import com.trek.core.entity.Admin;
import com.trek.core.dto.command.Command;
import lombok.Data;

@Data
public class AdminDeleteCommand implements Command {
    private String reason;
    private Admin admin;
}
