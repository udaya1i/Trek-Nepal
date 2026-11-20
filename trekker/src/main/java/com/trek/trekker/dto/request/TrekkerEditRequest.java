package com.trek.trekker.dto.request;

import com.trek.core.dto.request.Request;
import lombok.Data;

@Data
public class TrekkerEditRequest implements Request {

    private String fullName;
    private String username;
    private String bio;
    private String location;
}
