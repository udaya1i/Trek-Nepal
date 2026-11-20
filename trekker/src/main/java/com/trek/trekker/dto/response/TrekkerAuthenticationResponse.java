package com.trek.trekker.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trek.trekker.security.TrekkerPrinciple;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
public class TrekkerAuthenticationResponse {

    private boolean isAuthenticated;
    @JsonIgnore
    private String accessToken;
    @JsonIgnore
    private String refreshToken;
    private List<AuthoritiesResponse> authorities;
    @JsonIgnore
    private String message;


    public static TrekkerAuthenticationResponse successfulResponse(TrekkerPrinciple admin, String accessToken, String refreshToken) {
        TrekkerAuthenticationResponse response = new TrekkerAuthenticationResponse();
        response.setAuthenticated(true);
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setAuthorities(admin.getAuthorities().stream().map(AuthoritiesResponse::of).toList());
        return response;
    }

    public static TrekkerAuthenticationResponse failureResponse(String message) {
        TrekkerAuthenticationResponse response = new TrekkerAuthenticationResponse();
        response.setMessage(message);
        response.setAuthenticated(false);
        return response;
    }

    @Data
    static class AuthoritiesResponse {
        private String code;

        public static AuthoritiesResponse of(GrantedAuthority authority) {
            AuthoritiesResponse response = new AuthoritiesResponse();
            response.setCode(authority.getAuthority());
            return response;
        }
    }
}
