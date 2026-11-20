package com.trek.trekker.dto.request;

import com.trek.core.dto.request.Request;
import lombok.Data;

@Data
public class TrekkerCreateRequest implements Request {

    private String fullName;
    private String username;
    private String email;
    private String avatar;
    private String coverImage;
    private String bio;
    private String location;
    private String password;
}
