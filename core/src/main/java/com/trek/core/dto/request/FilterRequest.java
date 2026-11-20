package com.trek.core.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class FilterRequest {

    private List<FilterCriteriaRequest> filterCriteriaRequests;
    private PaginationRequest paginationRequest;
    private String search;

}

