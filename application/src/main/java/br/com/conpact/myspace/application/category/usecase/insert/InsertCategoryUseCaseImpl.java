package br.com.conpact.myspace.application.category.usecase.insert;

import br.com.conpact.myspace.domain.category.Category;
import br.com.conpact.myspace.domain.category.CategoryGateway;
import br.com.conpact.myspace.domain.validation.handler.ValidationHandler;

import java.util.Objects;

public class InsertCategoryUseCaseImpl extends InsertCategoryUseCase {
    private final CategoryGateway gateway;

    public InsertCategoryUseCaseImpl(final CategoryGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    @Override
    public Category execute(final Category category) {
        category.validate(new ValidationHandler());
        return this.gateway.insert(category);
    }
}
