package br.com.conpact.myspace.domain.validation.handler;

import java.util.List;

public interface Handler {

    void append(String error);

    List<String> getErrors();

}
