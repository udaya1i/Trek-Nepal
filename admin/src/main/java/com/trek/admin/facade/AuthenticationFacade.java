package com.trek.admin.facade;

import com.trek.admin.service.AdminAuthenticationService;
import com.trek.core.dto.request.LoginRequest;
import com.trek.admin.dto.response.AdminAuthenticationResponse;
import com.trek.core.dto.response.ServerResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationFacade {

    private final AdminAuthenticationService adminAuthenticationService;

    public ServerResponse<AdminAuthenticationResponse> login(LoginRequest request) {
        AdminAuthenticationResponse login = adminAuthenticationService.login(request);
        return login.isAuthenticated()
                ? ServerResponse.<AdminAuthenticationResponse>builder().data(login)
                .header("AccessToken", login.getAccessToken())
                .header("RefreshToken", login.getRefreshToken())
                .status(HttpStatus.OK)
                .message("Admin login successfully.").build() :
                ServerResponse.<AdminAuthenticationResponse>builder()
                        .status(HttpStatus.UNAUTHORIZED)
                        .message(login.getMessage())
                        .build();
    }
}
