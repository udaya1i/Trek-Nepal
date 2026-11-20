package com.trek.core.security.jwt.validator;

import com.trek.core.security.constant.ValidationResult;

public interface TokenValidator {

    ValidationResult validate(String token);
}
