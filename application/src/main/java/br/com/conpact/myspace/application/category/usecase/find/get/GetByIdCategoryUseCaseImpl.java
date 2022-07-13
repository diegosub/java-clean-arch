package br.com.conpact.myspace.application.category.usecase.find.get;

import br.com.conpact.myspace.domain.category.Category;
import br.com.conpact.myspace.domain.category.CategoryGateway;
import br.com.conpact.myspace.domain.exception.NotFoundException;

import java.util.Objects;

public class GetByIdCategoryUseCaseImpl extends GetByIdCategoryUseCase {

    private final CategoryGateway gateway;

    public GetByIdCategoryUseCaseImpl(final CategoryGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    @Override
    public Category execute(String id) {
        return gateway.getById(id)
                .orElseThrow(() -> NotFoundException.with("Categoria não encontrada"));
    }
}
