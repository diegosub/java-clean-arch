package br.com.conpact.myspace.application.category.usecase.activate;

import br.com.conpact.myspace.domain.category.Category;
import br.com.conpact.myspace.domain.category.CategoryGateway;

import java.util.Objects;

public class ActivateCategoryUseCaseImpl extends ActivateCategoryUseCase {

    private final CategoryGateway gateway;

    public ActivateCategoryUseCaseImpl(final CategoryGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }
    @Override
    public Category execute(String id) {
        return gateway.activate(id);
    }
}
