package com.trek.admin.dto.mapper;

import com.trek.core.dto.command.Command;
import com.trek.core.dto.request.Request;

public interface CommandMapper<R extends Request, C extends Command> {

    C convert(R request);

}
