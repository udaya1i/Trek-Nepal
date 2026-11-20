package com.trek.core.admin.service;

import com.trek.core.entity.Permit;
import com.trek.core.exception.RequestValidationException;
import com.trek.core.repository.TrekPermitRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TrekPermitQueryService {

    private final TrekPermitRepository trekPermitRepository;
    private final DestinationQueryService destinationQueryService;

    public Permit getById(Long id) {
        return trekPermitRepository.findById(id)
                .orElseThrow(() -> new RequestValidationException("Invalid permit"));
    }

    public List<Permit> getAllByStrategy(Long trekStrategyId) {
        return trekPermitRepository.findByDestination(destinationQueryService.getById(trekStrategyId));
    }

}
