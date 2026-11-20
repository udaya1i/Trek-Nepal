package com.trek.admin.dto.mapper;

import com.trek.admin.dto.command.AdminRoleCreateCommand;
import com.trek.core.entity.Role;
import com.trek.core.entity.RoleAuthorityMap;
import com.trek.core.entity.Authority;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class AdminRoleCreateRequestToEntityMapper {

    public Role toEntity(AdminRoleCreateCommand command) {
        Role role = new Role();
        role.setName(command.getName());
        role.setDescription(command.getDescription());
        role.setAuthorityMap(toMap(command.getAuthorities(), role));
        return role;
    }

    private List<RoleAuthorityMap> toMap(List<Authority> authorities, Role role) {
        return authorities.stream()
                .map(authority -> {
                    RoleAuthorityMap map = new RoleAuthorityMap();
                    map.setAuthority(authority);
                    map.setRole(role);
                    map.setActive(true);
                    return map;
                }).toList();
    }
}
