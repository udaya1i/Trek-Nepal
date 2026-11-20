package com.trek.admin.dto.command;

import com.trek.core.entity.TrekStrategy;
import com.trek.core.dto.command.Command;
import lombok.Data;

import java.util.List;

@Data
public class TrekPackagingAddCommand implements Command {

    private String name;
    private List<String> items;
    private TrekStrategy trekStrategy;

}
