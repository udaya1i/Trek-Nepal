package com.trek.admin.service;

import com.trek.admin.dto.command.AdminRoleCreateCommand;
import com.trek.admin.dto.command.AdminRoleUpdateCommand;
import com.trek.admin.dto.mapper.AdminRoleCreateRequestToEntityMapper;
import com.trek.core.entity.Role;
import com.trek.core.entity.RoleAuthorityMap;
import com.trek.core.entity.Authority;
import com.trek.core.repository.AdminRoleAuthorityMapRepository;
import com.trek.core.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author udaya
 */

@Service
@AllArgsConstructor
public class AdminRoleService {

    private final RoleRepository roleRepository;
    private final AdminRoleAuthorityMapRepository roleAuthorityMapRepository;

    @Transactional
    public Role create(AdminRoleCreateCommand command) {
        Role role = AdminRoleCreateRequestToEntityMapper.toEntity(command);
        return roleRepository.save(role);
    }

    @Transactional
    public void update(AdminRoleUpdateCommand command) {

        Role role = command.getRole();
        List<RoleAuthorityMap> existingMap = role.getAuthorityMap();
        List<Authority> newAuthorities = command.getAuthorities();

        existingMap.forEach(map -> map.setActive(false));

        newAuthorities.forEach(authority -> {
            RoleAuthorityMap roleAuthorityMap = existingMap.stream()
                    .filter(e -> e.getAuthority().getId().equals(authority.getId()))
                    .findFirst()
                    .orElseGet(() -> {
                        RoleAuthorityMap map = new RoleAuthorityMap();
                        map.setRole(role);
                        map.setAuthority(authority);
                        map.setActive(true);
                        existingMap.add(map);
                        return map;
                    });
            roleAuthorityMap.setActive(true);
        });
        roleAuthorityMapRepository.saveAll(existingMap);
        roleRepository.save(role);

    }


}
