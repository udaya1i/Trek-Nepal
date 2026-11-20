package com.trek.core.security.util;

import com.trek.core.dto.response.ServerResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@UtilityClass
public class AuthErrorMessageWriter {

    public static void writeErrorResponse(
            HttpServletResponse response,
            String errorCode,
            ObjectMapper objectMapper
    ) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(
                objectMapper.writeValueAsString(
                        ServerResponse.builder()
                                .status(HttpStatus.UNAUTHORIZED)
                                .errorCode(errorCode)
                                .build()
                )
        );
    }

    public static void writeErrorResponse(
            HttpServletResponse response,
            ObjectMapper objectMapper
    ) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(
                objectMapper.writeValueAsString(
                        ServerResponse.builder()
                                .status(HttpStatus.UNAUTHORIZED)
                                .build()
                )
        );
    }
}
