package com.trek.admin.dto.request;

import com.trek.core.dto.request.Request;
import lombok.Data;

import java.util.List;

@Data
public class TrekPackagingAddRequest implements Request {

    private String name;
    private List<String> items;
    private Long strategyId;
}
