package com.trek.admin.dto.request;

import com.trek.core.annotation.Required;
import com.trek.core.dto.request.Request;
import lombok.Data;

import java.util.List;

@Data
public class AdminRoleCreateRequest implements Request   {

    @Required(field = "name")
    private String name;

    @Required(field = "description")
    private String description;

    @Required(field = "authorities")
    private List<Long> authoritiesIds;
}
