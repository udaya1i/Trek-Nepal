package com.trek.core.admin.service;

import com.trek.core.entity.Role;
import com.trek.core.repository.RoleRepository;
import com.trek.core.exception.RequestValidationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminRoleQueryService {

    private final RoleRepository roleRepository;

    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    public Role getById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RequestValidationException("Admin role not found"));
    }


    public boolean existByName(String name) {
        return roleRepository.existsByName(name);
    }
}
