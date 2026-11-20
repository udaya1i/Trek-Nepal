package com.trek.core.admin.service;

import com.trek.core.entity.Authority;
import com.trek.core.repository.AdminAuthorityRepository;
import com.trek.core.exception.RequestValidationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminAuthoritiesQueryService {

    private final AdminAuthorityRepository adminAuthorityRepository;

    public Authority getById(Long id) {
        return adminAuthorityRepository.findById(id)
                .orElseThrow(() -> new RequestValidationException("Invalid authority id"));
    }
}
