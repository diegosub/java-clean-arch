package br.com.conpact.myspace.application;

public abstract class UseCase<T> {
    public abstract T execute(T t);
}
