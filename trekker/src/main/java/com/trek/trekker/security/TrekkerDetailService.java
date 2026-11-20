package com.trek.trekker.security;

import com.trek.core.entity.Trekker;
import com.trek.core.repository.TrekkerRepository;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrekkerDetailService implements UserDetailsService {

    private final TrekkerRepository trekkerRepository;

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Trekker trekker = trekkerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credential"));
        return new TrekkerPrinciple(trekker);
    }
}
