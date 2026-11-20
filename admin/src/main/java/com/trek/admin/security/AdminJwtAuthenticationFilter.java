package com.trek.admin.security;

 import com.trek.core.security.constant.ErrorCode;
import com.trek.core.security.constant.ValidationResult;
 import com.trek.core.security.util.AuthErrorMessageWriter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Objects;

/**
 * @author udaya
 */
@Component
@AllArgsConstructor
public class AdminJwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String REFRESH_TOKEN = "RefreshToken";

    private static final String BEARER = "Bearer ";
    private final AdminAccessTokenValidator adminAccessTokenValidator;
    private final AdminRefreshTokenValidator adminRefreshTokenValidator;
    private final AdminDetailService adminDetailService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);

        if (token != null) {
            ValidationResult validate = null;
            String authHeader = request.getHeader(AUTHORIZATION_HEADER);
            String refreshHeader = request.getHeader(REFRESH_TOKEN);
            if (authHeader!=null && authHeader.startsWith("Bearer")) {
                validate = adminAccessTokenValidator.validate(token);
            } else if (refreshHeader!=null && !refreshHeader.isBlank()) {
                validate = adminRefreshTokenValidator.validate(token);
            }
            if (ValidationResult.EXPIRED.equals(validate)) {
                AuthErrorMessageWriter.writeErrorResponse(response, ErrorCode.TOKEN_EXPIRED, objectMapper);
                return;
            }
            if (ValidationResult.INVALID.equals(validate)) {
                AuthErrorMessageWriter.writeErrorResponse(response, objectMapper);
                return;
            }
            if (ValidationResult.VALID.equals(validate)) {
                authenticateUser(token, request);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {

        String authorizationToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(authorizationToken) && authorizationToken.startsWith(BEARER)) {
            return authorizationToken.substring(BEARER.length());
        }
        String refreshToken = request.getHeader(REFRESH_TOKEN);
        if (StringUtils.hasText(refreshToken) && refreshToken.startsWith(BEARER)) {
            return refreshToken.substring(BEARER.length());
        }
        return null;
    }

    private void authenticateUser(String token, HttpServletRequest request) {
        String username = adminAccessTokenValidator.getUsername(token);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = adminDetailService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }
}
