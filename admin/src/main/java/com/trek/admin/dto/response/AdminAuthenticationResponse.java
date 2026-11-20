package com.trek.admin.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trek.admin.security.AdminPrinciple;
import com.trek.core.entity.Authority;
 import lombok.Data;

import java.util.List;

@Data
public class AdminAuthenticationResponse {

    private boolean isAuthenticated;
    @JsonIgnore
    private String accessToken;
    @JsonIgnore
    private String refreshToken;
    private List<AuthoritiesResponse> authorities;
    @JsonIgnore
    private String message;


    public static AdminAuthenticationResponse successfulResponse(AdminPrinciple admin, String accessToken, String refreshToken) {
        AdminAuthenticationResponse response = new AdminAuthenticationResponse();
        response.setAuthenticated(true);
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setAuthorities(admin.getAllAuthority().stream().map(AuthoritiesResponse::of).toList());
        return response;
    }

    public static AdminAuthenticationResponse failureResponse(String message) {
        AdminAuthenticationResponse response = new AdminAuthenticationResponse();
        response.setMessage(message);
        response.setAuthenticated(false);
        return response;
    }

    @Data
    static class AuthoritiesResponse {
        private String code;
        private String description;

        public static AuthoritiesResponse of(Authority authority) {
            AuthoritiesResponse response = new AuthoritiesResponse();
            response.setCode(authority.getCode());
            authority.setDescription(authority.getDescription());
            return response;
        }
    }

}
