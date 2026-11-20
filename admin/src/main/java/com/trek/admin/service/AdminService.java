package com.trek.admin.service;

import com.trek.admin.dto.command.AdminCreateCommand;
import com.trek.admin.dto.command.AdminEditCommand;
import com.trek.admin.dto.mapper.AdminCreateRequestToEntity;
import com.trek.core.entity.Admin;
import com.trek.core.repository.AdminRepository;
import com.trek.core.exception.RequestValidationException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public Admin create(AdminCreateCommand command) {
        Admin admin = AdminCreateRequestToEntity.of(command);
        admin.setPassword(passwordEncoder.encode(command.getPassword()));
        return adminRepository.save(admin);
    }

    public Admin edit(AdminEditCommand command) {
        Admin admin = command.getAdmin();
        admin.setName(command.getName());
        admin.setEmail(command.getEmail());
        admin.setAvatar(command.getAvatar());
        return adminRepository.save(admin);
    }

    public void delete(Admin admin) {
        adminRepository.save(admin);
    }

    public Admin getById(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new RequestValidationException("Admin not found"));
    }

    public List<Admin> getAll() {
        return adminRepository.findAll();
    }

}
