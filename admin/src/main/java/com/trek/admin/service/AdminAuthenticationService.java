package com.trek.admin.service;

import com.trek.admin.security.AdminPrinciple;
import com.trek.core.dto.request.LoginRequest;
import com.trek.admin.dto.response.AdminAuthenticationResponse;
import com.trek.core.security.jwt.provider.JwtTokenProvider;
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
public class AdminAuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider<AdminPrinciple> tokenProvider;

    public AdminAuthenticationResponse login(LoginRequest request) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            AdminPrinciple principal = (AdminPrinciple) authenticate.getPrincipal();
            assert principal != null;
//            if (!principal.isActive()) {
//                return AdminAuthenticationResponse.failureResponse("Admin is not active");
//            }
            String accessToken = tokenProvider.generateAccessToken(principal);
            String refreshToken = tokenProvider.generateRefreshToken(principal);
            return AdminAuthenticationResponse.successfulResponse(principal, accessToken, refreshToken);
        } catch (AuthenticationException ex) {
            return AdminAuthenticationResponse.failureResponse("Invalid credential!!!");
        }
    }

}
