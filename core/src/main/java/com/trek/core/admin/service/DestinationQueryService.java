package com.trek.core.admin.service;

import com.trek.core.entity.Destination;
import com.trek.core.exception.RequestValidationException;
import com.trek.core.repository.DestinationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class DestinationQueryService {

    private final DestinationRepository destinationRepository;

    @Transactional(readOnly = true)
    public Destination getById(Long id) {
        return destinationRepository.findById(id)
                .orElseThrow(() -> new RequestValidationException("Invalid destination"));
    }

    public List<Destination> getAll() {
        return destinationRepository.findAll();
    }

    public List<Destination> featuredTreks() {
        return destinationRepository.findTop5ByOrderByViewsDesc();
    }

}
