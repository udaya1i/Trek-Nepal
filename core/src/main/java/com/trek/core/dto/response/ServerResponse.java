package com.trek.core.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServerResponse<T> {

    @JsonIgnore
    private final MultiValueMap<String, String> headers;

    private String message;
    private HttpStatus httpStatus;
    private T data;
    private String errorCode;

    private ServerResponse(String message, HttpStatus httpStatus, T data,
                           MultiValueMap<String, String> headers, String errorCode) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.data = data;
        this.headers = headers;
        this.errorCode = errorCode;
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<T> {

        private final MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        private String message;
        private HttpStatus httpStatus;
        private T data;
        private String errorCode;

        public Builder<T> status(HttpStatus status) {
            this.httpStatus = status;
            return this;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> errorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public Builder<T> header(String key, String value) {
            this.headers.add(key, value);
            return this;
        }

        public ServerResponse<T> build() {
            return new ServerResponse<>(message, httpStatus, data, headers, errorCode);
        }
    }
}
