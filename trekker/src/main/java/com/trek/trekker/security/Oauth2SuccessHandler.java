package com.trek.trekker.security;

import com.trek.core.security.jwt.provider.JwtTokenProvider;
import com.trek.trekker.dto.request.TrekkerOauthResult;
import com.trek.trekker.service.TrekkerOauthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class Oauth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider<TrekkerPrinciple> tokenProvider;
    private final TrekkerOauthService trekkerOauthService;

    @Value("${app.frontend.base-url}")
    private String frontendBaseUrl;

    @Value("${app.frontend.oauth-callback-path}")
    private String oauthCallbackPath;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String googleId = oAuth2User.getAttribute("sub");
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        TrekkerOauthResult result = trekkerOauthService.findOrCreateFromOauth(googleId, email, name);

        TrekkerPrinciple principal = new TrekkerPrinciple(result.trekker());

        String accessToken = tokenProvider.generateAccessToken(principal);
        String refreshToken = tokenProvider.generateRefreshToken(principal);

        String redirectUrl = frontendBaseUrl + oauthCallbackPath
                + "?accessToken=" + URLEncoder.encode(accessToken, StandardCharsets.UTF_8)
                + "&refreshToken=" + URLEncoder.encode(refreshToken, StandardCharsets.UTF_8)
                + "&isNewUser=" + result.newUser();


        response.sendRedirect(redirectUrl);
    }
}