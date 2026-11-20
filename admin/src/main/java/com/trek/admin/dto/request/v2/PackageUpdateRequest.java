package com.trek.admin.dto.request.v2;

import com.trek.core.dto.request.Request;
import lombok.Data;

import java.util.List;

@Data
public class PackageUpdateRequest implements Request {

    private Long id;
    private String name;
    private List<String> items;
}
