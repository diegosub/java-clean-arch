package br.com.conpact.myspace.application.category.usecase.update;

import br.com.conpact.myspace.domain.category.Category;
import br.com.conpact.myspace.domain.category.CategoryGateway;
import br.com.conpact.myspace.domain.exception.NotFoundException;
import br.com.conpact.myspace.domain.validation.handler.ValidationHandler;

import java.util.Objects;

public class UpdateCategoryUseCaseImpl extends UpdateCategoryUseCase {
    private final CategoryGateway gateway;

    public UpdateCategoryUseCaseImpl(final CategoryGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    @Override
    public Category execute(final Category category) {
        final var model = gateway.getById(category.getId())
                .orElseThrow(() -> NotFoundException.with(Category.class, category.getId()));

        category.validate(new ValidationHandler());
        return this.gateway.update(category);
    }
}
