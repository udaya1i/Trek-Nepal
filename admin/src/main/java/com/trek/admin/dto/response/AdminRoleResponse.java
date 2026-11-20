package com.trek.admin.dto.response;

import com.trek.core.entity.Role;
import com.trek.core.entity.RoleAuthorityMap;
import com.trek.core.entity.Authority;
import lombok.Data;

import java.util.List;

@Data
public class AdminRoleResponse {
    private Long id;
    private String name;
    private String description;
    private List<AuthoritiesResponse> authorities;

    public static List<AdminRoleResponse> of(List<Role> role) {
        return role.stream().map(AdminRoleResponse::of).toList();
    }

    public static AdminRoleResponse of(Role role) {
        AdminRoleResponse response = new AdminRoleResponse();
        response.setId(role.getId());
        response.setName(role.getName());
        response.setDescription(role.getDescription());
        response.setAuthorities(ofAuthority(role.getAuthorityMap()));
        return response;
    }

    private static List<AuthoritiesResponse> ofAuthority(List<RoleAuthorityMap> roleAuthorityMaps) {
        return roleAuthorityMaps.stream()
                .filter(RoleAuthorityMap::isActive)
                .map(RoleAuthorityMap::getAuthority)
                .toList()
                .stream()
                .map(AdminRoleResponse::of)
                .toList();
    }


    private static AuthoritiesResponse of(Authority authority) {
        AuthoritiesResponse response = new AuthoritiesResponse();
        response.setName(authority.getCode());
        response.setDescription(authority.getDescription());
        return response;
    }

    @Data
    public static class AuthoritiesResponse {
        private String name;
        private String description;
    }
}
