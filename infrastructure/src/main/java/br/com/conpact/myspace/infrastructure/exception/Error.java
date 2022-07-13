package br.com.conpact.myspace.infrastructure.exception;

import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record Error(
        Integer status,
        Instant timestamp,
        String type,
        String title,
        String message,
        List<String> errors
) {
}
