package br.com.conpact.myspace.domain.exception;

import java.util.Collections;
import java.util.List;

public class NotFoundException extends NoStackTraceException {
    protected NotFoundException(final String message) {
        super(message);
    }

    public static NotFoundException with(String message) {
        return new NotFoundException(message);
    }

    public static NotFoundException with(
            final Class<?> classe,
            final String id
    ) {
        final var error = "%s with ID %s was not found".formatted(
                classe.getSimpleName(),
                id
        );
        return new NotFoundException(error);
    }
}
