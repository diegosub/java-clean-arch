package br.com.conpact.myspace.domain.category;

import br.com.conpact.myspace.domain.search.Pagination;
import br.com.conpact.myspace.domain.search.SearchQuery;

import java.util.Optional;

public interface CategoryGateway {

    Category insert(Category category);

    Category update(Category category);

    Optional<Category> getById(String id);

    Category activate(String id);

    Category deactivate(String id);

    Pagination<Category> findAll(SearchQuery query);

}
