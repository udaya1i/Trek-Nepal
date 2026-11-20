package com.trek.admin.dto.command;

import com.trek.core.entity.TrekPacking;
import com.trek.core.dto.command.Command;
import lombok.Data;

import java.util.List;

@Data
public class TrekPackagingUpdateCommand implements Command {

    private TrekPacking trekPacking;
    private String name;
    private List<String> items;

}