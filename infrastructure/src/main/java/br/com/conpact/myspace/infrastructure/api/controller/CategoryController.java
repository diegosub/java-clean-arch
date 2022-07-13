package br.com.conpact.myspace.infrastructure.api.controller;

import br.com.conpact.myspace.application.category.usecase.find.get.GetByIdCategoryUseCase;
import br.com.conpact.myspace.application.category.usecase.find.list.ListCategoryUseCase;
import br.com.conpact.myspace.application.category.usecase.insert.InsertCategoryUseCase;
import br.com.conpact.myspace.application.category.usecase.update.UpdateCategoryUseCase;
import br.com.conpact.myspace.domain.category.Category;
import br.com.conpact.myspace.domain.search.Pagination;
import br.com.conpact.myspace.domain.search.SearchQuery;
import br.com.conpact.myspace.infrastructure.api.CategoryAPI;
import br.com.conpact.myspace.infrastructure.category.mapper.CategoryMapper;
import br.com.conpact.myspace.infrastructure.category.model.CategoryResponse;
import br.com.conpact.myspace.infrastructure.category.model.CreateCategoryRequest;
import br.com.conpact.myspace.infrastructure.category.model.UpdateCategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class CategoryController implements CategoryAPI {

    @Autowired
    private InsertCategoryUseCase insertCategoryUseCase;

    @Autowired
    private GetByIdCategoryUseCase getByIdCategoryUseCase;

    @Autowired
    private UpdateCategoryUseCase updateCategoryUseCase;

    @Autowired
    private ListCategoryUseCase listCategoryUseCase;


    @Override
    public ResponseEntity<CategoryResponse> insert(final CreateCategoryRequest request) {
        var category = Category.create(request.name());
        category = insertCategoryUseCase.execute(category);
        return ResponseEntity.created(URI.create("/categories/" + category.getId())).body(CategoryMapper.toResponse(category));
    }

    @Override
    public ResponseEntity<CategoryResponse> update(String id, UpdateCategoryRequest input) {
        var category = Category.create(id, input.name());
        return ResponseEntity.ok(CategoryMapper.toResponse(updateCategoryUseCase.execute(category)));
    }

    @Override
    public Pagination<CategoryResponse> listAll(String search, int page, int perPage, String sort, String direction) {
        return listCategoryUseCase.execute(
                new SearchQuery(
                        page,
                        perPage,
                        search,
                        sort,
                        direction
                )
        ).map(CategoryMapper::toResponse);
    }

    @Override
    public ResponseEntity<CategoryResponse> getById(String id) {
        return ResponseEntity.ok(CategoryMapper.toResponse(getByIdCategoryUseCase.execute(id)));
    }

}
