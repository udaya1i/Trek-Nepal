package com.trek.trekker.security;

import com.trek.core.entity.Trekker;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal; // ✅ import this
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @param trekker
 * @author udaya
 */
public record TrekkerPrinciple(Trekker trekker) implements UserDetails, Principal {

    @NullMarked
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return trekker.getRole().getAuthorityMap().stream()
                .map(t -> new SimpleGrantedAuthority(t.getAuthority().getCode()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getName() {
        return trekker.getUsername();
    }

    @Override
    public String getPassword() {
        return trekker.getPassword();
    }

    @Override
    @NullMarked
    public String getUsername() {
        return trekker.getUsername();
    }

    public Trekker getTrekker() {
        return trekker;
    }

    public Long getTrekkerId() {
        return trekker.getId();
    }
}