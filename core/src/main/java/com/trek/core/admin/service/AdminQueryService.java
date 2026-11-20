package com.trek.core.admin.service;

import com.trek.core.entity.Admin;
import com.trek.core.repository.AdminRepository;
import com.trek.core.exception.RequestValidationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminQueryService {

    private final AdminRepository adminRepository;

    public Admin getById(Long id) {
        return adminRepository.findById(id).orElseThrow(() -> new RequestValidationException(("Admin not found")));
    }

    public Optional<Admin> getByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    public boolean findByUsername(String username) {
        return adminRepository.existsByUsername(username);
    }


}
