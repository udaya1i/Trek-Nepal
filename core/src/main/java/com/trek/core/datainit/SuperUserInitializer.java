package com.trek.core.datainit;

import com.trek.core.entity.Admin;
import com.trek.core.entity.Role;
import com.trek.core.entity.RoleAuthorityMap;
import com.trek.core.entity.Authority;
import com.trek.core.repository.AdminRepository;
import com.trek.core.repository.AdminRoleAuthorityMapRepository;
import com.trek.core.repository.RoleRepository;
import com.trek.core.repository.AuthorityRepository;
import com.trek.core.datainit.yaml.SuperAdminYaml;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SuperUserInitializer implements BaseInitializer {

    private final SuperAdminYaml yaml;

    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;
    private final AdminRepository adminRepository;
    private final AdminRoleAuthorityMapRepository mapRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void initialize() {
        log.info("************ Super User Initialization Started ************");


        if (adminRepository.existsByUsername(yaml.getAdmin().getUsername())) {
            log.info("Super admin already exists. Skipping...");
            return;
        }

        List<Authority> savedAuthorities = yaml.getAuthorities().stream().map(a -> {
            Authority authority = new Authority();
            authority.setCode(a.getCode());
            authority.setDescription(a.getDescription());
            return authorityRepository.save(authority);
        }).toList();

        Role role = new Role();
        role.setName(yaml.getRole().getName());
        role.setDescription(yaml.getRole().getDescription());

        Role savedRole = roleRepository.save(role);

        List<RoleAuthorityMap> maps = savedAuthorities.stream().map(auth -> {
            RoleAuthorityMap map = new RoleAuthorityMap();
            map.setAuthority(auth);
            map.setRole(savedRole);
            map.setActive(true);
            return map;
        }).toList();

        mapRepository.saveAll(maps);
        savedRole.setAuthorityMap(maps);

        Admin admin = new Admin();
        admin.setName(yaml.getAdmin().getName());
        admin.setUsername(yaml.getAdmin().getUsername());
        admin.setEmail(yaml.getAdmin().getEmail());
        admin.setPassword(passwordEncoder.encode(yaml.getAdmin().getPassword()));
        admin.setSuperPassword(passwordEncoder.encode(yaml.getAdmin().getSuperPassword()));
        admin.setIsVerified(yaml.getAdmin().getIsVerified());
        admin.setSuperAdmin(yaml.getAdmin().getIsSuperAdmin());
        admin.setRole(savedRole);

        adminRepository.save(admin);

        log.info("************ Super User Initialization Completed ************");
    }
}