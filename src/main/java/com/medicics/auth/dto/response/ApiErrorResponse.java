package com.medicics.auth.dto.response;

import java.time.Instant;
import java.util.Map;

public record ApiErrorResponse(
        Instant timestamp,
        int status,
        String error,
        Map<String, String> campos
) {
    public static ApiErrorResponse of(int status, String error) {
        return new ApiErrorResponse(Instant.now(), status, error, null);
    }

    public static ApiErrorResponse of(int status, String error, Map<String, String> campos) {
        return new ApiErrorResponse(Instant.now(), status, error, campos);
    }
}
