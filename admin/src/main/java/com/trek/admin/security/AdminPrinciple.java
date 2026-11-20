package com.trek.admin.security;


import com.trek.core.constants.AdminStatus;
import com.trek.core.entity.Admin;
import com.trek.core.entity.RoleAuthorityMap;
import com.trek.core.entity.Authority;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @param admin
 * @author udaya
 */
public record AdminPrinciple(Admin admin) implements UserDetails {

    @Override
    @NullMarked
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return admin.getRole().getAuthorityMap().stream()
                .filter(RoleAuthorityMap::isActive)
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority().getCode()))
                .collect(Collectors.toSet());
    }

    public Admin getAdmin() {
        return admin;
    }

    public boolean isActive() {
        return AdminStatus.ACTIVE.getId().equals(admin.getStatus());
    }

    @Override
    public @Nullable String getPassword() {
        return admin.getPassword();
    }

    @Override
    @NullMarked
    public String getUsername() {
        return admin.getUsername();
    }

    public List<Authority> getAllAuthority() {
        return this.admin.getRole()
                .getAuthorityMap()
                .stream()
                .filter(RoleAuthorityMap::isActive)
                .map(RoleAuthorityMap::getAuthority)
                .collect(Collectors.toList());
    }

}
