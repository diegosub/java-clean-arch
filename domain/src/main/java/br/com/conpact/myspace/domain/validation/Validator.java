package br.com.conpact.myspace.domain.validation;

import br.com.conpact.myspace.domain.validation.handler.ValidationHandler;

public abstract class Validator {

    private final ValidationHandler handler;

    protected Validator(final ValidationHandler handler) {
        this.handler = handler;
    }
    public abstract void validate();

    public ValidationHandler validationHandler() {
        return this.handler;
    }
}
