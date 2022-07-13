package br.com.conpact.myspace.application.category.usecase.inactivate;

import br.com.conpact.myspace.domain.category.Category;
import br.com.conpact.myspace.domain.category.CategoryGateway;

import java.util.Objects;

public class DeactivateCategoryUseCaseImpl extends DeactivateCategoryUseCase {

    private final CategoryGateway gateway;

    public DeactivateCategoryUseCaseImpl(final CategoryGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }
    @Override
    public Category execute(String id) {
        return gateway.deactivate(id);
    }
}
