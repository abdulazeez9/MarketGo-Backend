package com.marketgo.common.entity;


import org.springframework.data.domain.Page;

import java.util.List;

public record Pagination<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
    public static <T> Pagination<T> from(Page<T> page) {
        return new Pagination<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
