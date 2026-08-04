package com.devsuperior.dscommerce.dto;

import java.time.Instant;

public record CustomError(
        Instant timestamp,
        int status,
        String error,
        String path
) {
    public static CustomError of(int status, String error, String path) {
        return new CustomError(Instant.now(), status, error, path);
    }
}
