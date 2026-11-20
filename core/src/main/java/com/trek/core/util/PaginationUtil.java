package com.trek.core.util;

import com.trek.core.dto.request.PaginationRequest;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author udaya
 */
@UtilityClass
public class PaginationUtil {

    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int MAX_PAGE_SIZE = 5000;

    public Pageable of(PaginationRequest request) {
        if (request == null) {
            return PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
        }
        return PageRequest.of(
                page(request.getPage()),
                size(request.getSize()),
                sort(request.getSortBy())
        );
    }

    private int page(Integer page) {
        return (page == null || page < 0)
                ? DEFAULT_PAGE_NUMBER
                : page;
    }

    private int size(Integer size) {
        if (size == null || size <= 0) {
            return DEFAULT_PAGE_SIZE;
        }
        return Math.min(size, MAX_PAGE_SIZE);
    }

    private Sort sort(String sortBy) {
        if (sortBy == null || sortBy.isBlank()) {
            return Sort.unsorted();
        }

        if (sortBy.startsWith("-")) {
            return Sort.by(sortBy.substring(1)).descending();
        }

        return Sort.by(sortBy).ascending();
    }
}
