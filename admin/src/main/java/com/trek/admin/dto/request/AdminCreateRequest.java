package com.trek.admin.dto.request;

import com.trek.core.annotation.Required;
import com.trek.core.dto.request.Request;
import lombok.Data;

@Data
public class AdminCreateRequest implements Request {

    @Required(field = "name")
    private String name;

    @Required(field = "username")
    private String username;

    @Required(field = "email")
    private String email;

    @Required(field = "avatar")
    private String avatar;

    private boolean isSuperAdmin;

    @Required(field = "password")
    private String password;

    private String superPassword;

    private Long adminRole;
}
