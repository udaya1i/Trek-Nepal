package com.trek.core.admin.service;

import com.trek.core.entity.TrekStrategy;
import com.trek.core.exception.RequestValidationException;
import com.trek.core.repository.TrekStrategyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TrekStrategyQueryService {

    private final TrekStrategyRepository trekStrategyRepository;
    private final DestinationQueryService destinationQueryService;

    @Transactional(readOnly = true)
    public TrekStrategy getById(Long id) {
        return trekStrategyRepository.findById(id)
                .orElseThrow(() -> new RequestValidationException("Invalid strategy"));
    }

    public TrekStrategy findById(Long id) {
        return trekStrategyRepository.findById(id)
                .orElseThrow(() -> new RequestValidationException("Invalid strategy"));
    }

    @Transactional
    public List<TrekStrategy> getByDestination(Long id) {
        return trekStrategyRepository.findByDestination(destinationQueryService.getById(id));
    }
}
