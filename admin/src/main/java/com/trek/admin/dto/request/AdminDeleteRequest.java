package com.trek.admin.dto.request;

import com.trek.core.dto.request.Request;
import lombok.Data;

@Data
public class AdminDeleteRequest implements Request {
    private String remarks;
}
