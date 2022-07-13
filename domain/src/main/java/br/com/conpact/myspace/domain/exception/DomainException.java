package br.com.conpact.myspace.domain.exception;

import java.util.List;

public class DomainException extends NoStackTraceException {

    private final List<String> errors;

    protected DomainException(final String message, final List<String> errors) {
        super(message);
        this.errors = errors;
    }

    public static DomainException with(final String message) {
        return new DomainException(message, null);
    }

    public static DomainException with(final String message, final List<String> errors) {
        return new DomainException(message, errors);
    }

    public List<String> getErrors() {
        return errors;
    }
}
