package com.trek.admin.dto.request.v2;

import com.trek.core.annotation.Required;
import com.trek.core.dto.request.Request;
import lombok.Data;

@Data
public class DestinationPublishRequest implements Request {

    @Required(field = "Destination Id")
    private Long id;
}
