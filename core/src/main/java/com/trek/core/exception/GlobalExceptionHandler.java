package com.trek.core.exception;


import com.trek.core.dto.response.ServerResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ServerResponse> defaultExceptionHandler(Exception e) {
        e.printStackTrace();
        ServerResponse build = ServerResponse.builder().message(e.getMessage()).status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(build);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ServerResponse> unAuthorizedExceptionHandler(Exception e) {
        e.printStackTrace();
        ServerResponse build = ServerResponse.builder().message(e.getMessage()).status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(build);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ServerResponse> constraintViolationException(ConstraintViolationException e) {
        e.printStackTrace();
        ServerResponse build = ServerResponse.builder().message(e.getMessage()).status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(build);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ServerResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception) {
        log.error("e: ", exception);
        exception.printStackTrace();
        ServerResponse errorResponse = ServerResponse.builder().message(exception.getMessage()).status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ex.printStackTrace();
        String errorMessages = ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
        ServerResponse errorResponse = ServerResponse.builder().message(errorMessages).status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.badRequest().body(errorResponse);
    }


    @ExceptionHandler(RequestValidationException.class)
    public ResponseEntity<ServerResponse> requestValidationException(RequestValidationException e) {
        e.printStackTrace();
        ServerResponse build = ServerResponse.builder().message(e.getMessage()).status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(build);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            org.springframework.http.converter.HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        ex.printStackTrace();

        ServerResponse errorResponse = ServerResponse.builder()
                .message("Invalid request format: " + ex.getMostSpecificCause().getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }



}
