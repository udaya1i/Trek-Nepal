package com.trek.trekker.dto.request;

import com.trek.core.dto.request.Request;
import lombok.Data;

@Data
public class TrekkerDeleteRequest implements Request {

    private String password;
}
