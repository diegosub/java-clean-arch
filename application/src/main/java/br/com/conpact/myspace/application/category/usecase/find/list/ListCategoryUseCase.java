package br.com.conpact.myspace.application.category.usecase.find.list;

import br.com.conpact.myspace.application.UseCaseOUT;
import br.com.conpact.myspace.domain.category.Category;
import br.com.conpact.myspace.domain.search.Pagination;
import br.com.conpact.myspace.domain.search.SearchQuery;

public abstract class ListCategoryUseCase extends UseCaseOUT<SearchQuery, Pagination<Category>> {
}
