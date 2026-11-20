package com.trek.trekker.controller;

import com.trek.core.constants.PublicPathConstant;
import com.trek.core.dto.request.ForgotPasswordRequest;
import com.trek.core.dto.request.LoginRequest;
import com.trek.core.dto.request.ResetPasswordRequest;
import com.trek.core.dto.response.ServerResponse;
import com.trek.core.security.constant.Authorities;
import com.trek.trekker.dto.response.TrekkerAuthenticationResponse;
import com.trek.trekker.dto.response.TrekkerDetailResponse;
import com.trek.trekker.facade.TrekkerAuthenticationFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PublicPathConstant.BASE_URL + PublicPathConstant.AUTHENTICATION)
@AllArgsConstructor
public class TrekkerAuthenticationController {

    private final TrekkerAuthenticationFacade authenticationFacade;

    @PostMapping(PublicPathConstant.LOGIN)
    public ResponseEntity<ServerResponse<TrekkerAuthenticationResponse>> login(@RequestBody LoginRequest loginRequest) {
        ServerResponse<TrekkerAuthenticationResponse> authenticate = authenticationFacade.authenticate(loginRequest);
        return ResponseEntity
                .status(authenticate.getHttpStatus())
                .headers(HttpHeaders.readOnlyHttpHeaders(authenticate.getHeaders()))
                .body(authenticate);
    }

    @GetMapping(PublicPathConstant.CHECK_USERNAME + PublicPathConstant.KEYWORD)
    public ResponseEntity<ServerResponse<Void>> checkUsername(@PathVariable("keyword") String username) {
        ServerResponse<Void> authenticate = authenticationFacade.checkUsername(username);
        return ResponseEntity.status(authenticate.getHttpStatus()).body(authenticate);
    }

    @GetMapping(PublicPathConstant.CHECK_EMAIL + PublicPathConstant.KEYWORD)
    public ResponseEntity<ServerResponse<Void>> checkEmail(@PathVariable("keyword") String email) {
        ServerResponse<Void> authenticate = authenticationFacade.checkEmail(email);
        return ResponseEntity.status(authenticate.getHttpStatus()).body(authenticate);
    }

    @PreAuthorize(Authorities.TREKKER_PROFILE)
    @GetMapping(PublicPathConstant.USER_DETAIL)
    public ResponseEntity<ServerResponse<TrekkerDetailResponse>> authUserDetail() {
        ServerResponse<TrekkerDetailResponse> authenticate = authenticationFacade.authUserDetail();
        return ResponseEntity
                .status(authenticate.getHttpStatus())
                .headers(HttpHeaders.readOnlyHttpHeaders(authenticate.getHeaders()))
                .body(authenticate);
    }

    @GetMapping(PublicPathConstant.REFRESH)
    public ResponseEntity<ServerResponse<TrekkerDetailResponse>> refresh() {
        ServerResponse<TrekkerDetailResponse> authenticate = authenticationFacade.authUserDetail();
        return ResponseEntity
                .status(authenticate.getHttpStatus())
                .headers(HttpHeaders.readOnlyHttpHeaders(authenticate.getHeaders()))
                .body(authenticate);
    }

    @PostMapping(PublicPathConstant.FORGOT_PASSWORD)
    public ResponseEntity<ServerResponse<Void>> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        ServerResponse<Void> response = authenticationFacade.forgotPassword(request);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @PostMapping(PublicPathConstant.RESET_PASSWORD)
    public ResponseEntity<ServerResponse<Void>> resetPassword(@RequestBody ResetPasswordRequest request) {
        ServerResponse<Void> response = authenticationFacade.resetPassword(request);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}
