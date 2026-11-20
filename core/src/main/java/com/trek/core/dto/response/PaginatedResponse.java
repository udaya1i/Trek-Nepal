package com.trek.core.dto.response;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.util.List;
@Data
public class PaginatedResponse<T> {
    private String message;
    private HttpStatus status;
    private List<T> data;
    private int page;
    private int size;
    private long total;
    private int totalPages;

    public static <T> Builder<T> data(Page<T> page) {
        return new Builder<>(page);
    }

    public static class Builder<T> {
        private final Page<T> page;
        private String message;
        private HttpStatus status;

        private Builder(Page<T> page) {
            this.page = page;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public PaginatedResponse<T> build() {
            PaginatedResponse<T> response = new PaginatedResponse<>();
            response.setData(page.getContent());
            response.setPage(page.getNumber());
            response.setSize(page.getSize());
            response.setTotal(page.getTotalElements());
            response.setTotalPages(page.getTotalPages());
            response.setMessage(message);
            response.setStatus(status);
            return response;
        }
    }
}