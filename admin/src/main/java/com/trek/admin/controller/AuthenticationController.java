package com.trek.admin.controller;

import com.trek.admin.dto.response.AdminAuthenticationResponse;
import com.trek.admin.facade.AuthenticationFacade;
import com.trek.core.constants.AdminPathConstant;
import com.trek.core.dto.request.LoginRequest;
import com.trek.core.dto.response.ServerResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping( "api/v1/auth/admin")
public class AuthenticationController {

    private final AuthenticationFacade authenticationFacade;

    @PostMapping(AdminPathConstant.LOGIN)
    public ResponseEntity<ServerResponse<AdminAuthenticationResponse>> login(@RequestBody @Valid LoginRequest loginRequest) {
        ServerResponse<AdminAuthenticationResponse> response = authenticationFacade.login(loginRequest);
        return ResponseEntity.status(response.getHttpStatus())
                .headers(new HttpHeaders(response.getHeaders()))
                .body(response);
     }

//    @PostMapping(ApiPath.LOGOUT)
//    public ResponseEntity<ServerResponse> logout() {
//        return ResponseEntity.status(HttpStatus.OK).body(new ServerResponse());
//    }
//
//    @PostMapping(ApiPath.REFRESH)
//    public ResponseEntity<ServerResponse> refresh() {
//        return ResponseEntity.status(HttpStatus.OK).body(new ServerResponse());
//    }
}
