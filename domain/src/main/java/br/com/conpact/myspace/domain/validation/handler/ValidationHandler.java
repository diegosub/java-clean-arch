package br.com.conpact.myspace.domain.validation.handler;

import java.util.ArrayList;
import java.util.List;

public class ValidationHandler implements Handler {

    List<String> errors;

    public ValidationHandler() {
        errors = new ArrayList<String>();
    }

    public void append(String error) {
        errors.add(error);
    }

    public List<String> getErrors() {
        return this.errors;
    }

}
