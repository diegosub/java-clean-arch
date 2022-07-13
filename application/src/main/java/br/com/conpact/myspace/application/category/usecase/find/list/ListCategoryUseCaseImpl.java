package br.com.conpact.myspace.application.category.usecase.find.list;

import br.com.conpact.myspace.domain.category.Category;
import br.com.conpact.myspace.domain.category.CategoryGateway;
import br.com.conpact.myspace.domain.search.Pagination;
import br.com.conpact.myspace.domain.search.SearchQuery;

import java.util.Objects;

public class ListCategoryUseCaseImpl extends ListCategoryUseCase {

    private final CategoryGateway gateway;

    public ListCategoryUseCaseImpl(final CategoryGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    @Override
    public Pagination<Category> execute(SearchQuery query) {
        return gateway.findAll(query);
    }
}
