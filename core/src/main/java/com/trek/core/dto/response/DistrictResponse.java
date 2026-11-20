package com.trek.core.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DistrictResponse(Long id, String name, Long provinceId) {
}