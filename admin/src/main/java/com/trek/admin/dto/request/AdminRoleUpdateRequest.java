package com.trek.admin.dto.request;

import com.trek.core.dto.request.Request;
import lombok.Data;

import java.util.List;

@Data
public class AdminRoleUpdateRequest implements Request {

    private Long id;
    private String name;
    private String description;
    private List<Long> updatedIds;
}
