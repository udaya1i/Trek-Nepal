package com.trek.admin.dto.response;

import com.trek.core.entity.TrekStrategy;

public record TrekStrategyAddResponse(Long id) {
    public static TrekStrategyAddResponse from(TrekStrategy strategy) {
        return new TrekStrategyAddResponse(strategy.getId());
    }
}
