package com.trek.core.dto.request;

import lombok.Data;

@Data
public class PaginationRequest {

    private Integer size;
    private String sortBy;
    private Integer page;

}
