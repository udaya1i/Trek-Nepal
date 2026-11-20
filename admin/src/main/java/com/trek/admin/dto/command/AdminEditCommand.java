package com.trek.admin.dto.command;

import com.trek.core.entity.Admin;
import com.trek.core.dto.command.Command;
import lombok.Data;

@Data
public class AdminEditCommand implements Command {

    private String name;
    private String email;
    private String avatar;
    private Admin admin;

}
