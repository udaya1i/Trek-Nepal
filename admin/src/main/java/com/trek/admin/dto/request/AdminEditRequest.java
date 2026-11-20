package com.trek.admin.dto.request;

import com.trek.core.dto.request.Request;
import lombok.Data;

@Data
public class AdminEditRequest implements Request {

    private Long id;
    private String username;
    private String name;
    private String email;
    private String avatar;

}
