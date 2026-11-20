package com.trek.core.datainit;

import com.trek.core.datainit.yaml.TrekkerUserYaml;
import com.trek.core.entity.Authority;
import com.trek.core.entity.Role;
import com.trek.core.entity.RoleAuthorityMap;
import com.trek.core.repository.AdminRoleAuthorityMapRepository;
import com.trek.core.repository.RoleRepository;
import com.trek.core.repository.AuthorityRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class TrekkerRoleInitializer implements BaseInitializer {

    private final TrekkerUserYaml yaml;
    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;
    private final AdminRoleAuthorityMapRepository mapRepository;

    @Override
    @Transactional
    public void initialize() {
        log.info("************ Trekker Role Initialization started ************");

        if (roleRepository.existsByName(yaml.getRole().getName())) {
            log.info("Trekker role already exists. Skipping...");
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

        log.info("************ Trekker Role Initialization Completed ************");
    }
}