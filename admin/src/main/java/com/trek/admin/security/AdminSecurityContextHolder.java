package com.trek.admin.security;


import com.trek.core.entity.Admin;
import com.trek.core.security.util.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author udaya
 */
@Component
public class AdminSecurityContextHolder implements SecurityContextHolder {

    @Override
    public Admin getCurrentUser() {
        Authentication auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof AdminPrinciple principle)) {
            return null;
        }
        return principle.getAdmin();
    }

    @Override
    public String getLoggedUsername() {
        Authentication auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        return auth != null ? auth.getName() : null;
    }

    @Override
    public List<String> getAuthorities() {
        Authentication auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return List.of();

        return auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }
}