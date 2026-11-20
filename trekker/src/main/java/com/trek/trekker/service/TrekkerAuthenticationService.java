package com.trek.trekker.service;

import com.trek.core.dto.request.LoginRequest;
import com.trek.core.entity.Trekker;
import com.trek.core.security.jwt.provider.JwtTokenProvider;
import com.trek.core.trekker.service.TrekkerQueryService;
import com.trek.trekker.dto.response.TrekkerAuthenticationResponse;
import com.trek.trekker.security.TrekkerPrinciple;
import com.trek.trekker.security.TrekkerSecurityContextHolderHolder;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

/**
 * @author udaya
 */
@Service
@AllArgsConstructor
public class TrekkerAuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider<TrekkerPrinciple> tokenProvider;
    private final TrekkerSecurityContextHolderHolder trekkerSecurityContextHolderHolder;
    private final TrekkerQueryService trekkerQueryService;

    public TrekkerAuthenticationResponse login(LoginRequest request) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            TrekkerPrinciple principal = (TrekkerPrinciple) authenticate.getPrincipal();
            assert principal != null;

            String accessToken = tokenProvider.generateAccessToken(principal);
            String refreshToken = tokenProvider.generateRefreshToken(principal);

            return TrekkerAuthenticationResponse.successfulResponse(principal, accessToken, refreshToken);

        } catch (AuthenticationException e) {
            return TrekkerAuthenticationResponse.failureResponse("Invalid credential!!");
        }
    }

    public boolean checkUsername(String username) {
        return trekkerQueryService.existsByUsername(username);
    }

    public boolean checkEmail(String email) {
        return trekkerQueryService.existByEmail(email);
    }

    public Trekker authUserDetail() {
        return trekkerSecurityContextHolderHolder.getCurrentUser();
    }


}
