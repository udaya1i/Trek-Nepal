package com.trek.trekker.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2FailureHandler implements AuthenticationFailureHandler {

    @Value("${app.frontend.base-url}")
    private String frontendBaseUrl;

    @Value("${app.frontend.oauth-callback-path}")
    private String oauthCallbackPath;

    @Override
    public void onAuthenticationFailure(@NonNull HttpServletRequest request, HttpServletResponse response,
                                        @NonNull AuthenticationException exception) throws IOException {
        response.sendRedirect(frontendBaseUrl + oauthCallbackPath + "?error=oauth_failed");
    }
}