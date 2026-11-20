package com.trek.core.admin.service;

import com.trek.core.entity.TrekPacking;
import com.trek.core.exception.RequestValidationException;
import com.trek.core.repository.TrekPackagingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrekPackagingQueryService {

    private final TrekPackagingRepository trekPackagingRepository;

    public TrekPacking getById(Long id) {
        return trekPackagingRepository.findById(id)
                .orElseThrow(() -> new RequestValidationException("Invalid packaging"));
    }

}