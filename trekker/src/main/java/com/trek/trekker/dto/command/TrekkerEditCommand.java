package com.trek.trekker.dto.command;

import com.trek.core.dto.command.Command;
import com.trek.core.entity.Trekker;
import lombok.Data;

@Data
public class TrekkerEditCommand implements Command {

    private Trekker trekker;
    private String fullName;
    private String username;
    private String bio;
    private String location;
}
