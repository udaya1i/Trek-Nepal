package com.trek.trekker.security;

import com.trek.core.entity.Trekker;
import com.trek.core.security.util.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;

/**
 * @author udaya
 */
@Component
public class TrekkerSecurityContextHolderHolder implements SecurityContextHolder {

    public Trekker getCurrentUser() {
        Authentication auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof TrekkerPrinciple principal)) {
            return null;
        }

        return principal.getTrekker();
    }

    public Trekker getCurrentUser(Principal principal) {
        if (principal instanceof UsernamePasswordAuthenticationToken auth
                && auth.getPrincipal() instanceof TrekkerPrinciple p) {
            return p.getTrekker();
        }
        return null;
    }

    @Override
    public String getLoggedUsername() {
        Trekker trekker = getCurrentUser();
        return trekker != null ? trekker.getUsername() : null;
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