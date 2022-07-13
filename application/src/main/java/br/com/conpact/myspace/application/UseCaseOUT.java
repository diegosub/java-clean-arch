package br.com.conpact.myspace.application;

public abstract class UseCaseOUT<IN, OUT> {
    public abstract OUT execute(IN in);
}
