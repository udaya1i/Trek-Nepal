package com.trek.admin.security;

import com.trek.core.security.constant.ValidationResult;
import com.trek.core.security.jwt.validator.TokenValidator;
import org.springframework.stereotype.Service;

@Service
public class AdminRefreshTokenValidator implements TokenValidator {

    @Override
    public ValidationResult validate(String token) {
        return ValidationResult.VALID;
    }
}
