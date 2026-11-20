package com.trek.trekker.dto.command;

import com.trek.core.dto.command.Command;
import lombok.Data;

@Data
public class TrekkerCreateCommand implements Command {

    private String fullName;
    private String username;
    private String email;
    private String avatar;
    private String coverImage;
    private String bio;
    private String location;
    private String password;
    private String oldPassword;
}
