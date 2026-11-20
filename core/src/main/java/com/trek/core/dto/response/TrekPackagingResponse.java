package com.trek.core.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TrekPackagingResponse(String name, List<String> items) {

}
