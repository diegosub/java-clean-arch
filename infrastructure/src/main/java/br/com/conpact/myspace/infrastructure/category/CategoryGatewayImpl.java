package br.com.conpact.myspace.infrastructure.category;

import br.com.conpact.myspace.domain.category.Category;
import br.com.conpact.myspace.domain.category.CategoryGateway;
import br.com.conpact.myspace.domain.search.Pagination;
import br.com.conpact.myspace.domain.search.SearchQuery;
import br.com.conpact.myspace.infrastructure.category.persistence.CategoryJpa;
import br.com.conpact.myspace.infrastructure.category.persistence.CategoryRepository;
import br.com.conpact.myspace.infrastructure.utils.SpecificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryGatewayImpl implements CategoryGateway {

    @Autowired
    private CategoryRepository repository;

    @Override
    public Category insert(final Category category) {
        return repository.save(CategoryJpa.toPersistense(category)).toDomain();
    }

    @Override
    public Category update(final Category category) {
        return repository.save(CategoryJpa.toPersistense(category)).toDomain();
    }

    @Override
    public Optional<Category> getById(final String id) {
        return this.repository.findById(id)
                .map(CategoryJpa::toDomain);
    }

    @Override
    public Category activate(String id) {
        return null;
    }

    @Override
    public Category deactivate(String id) {
        return null;
    }

    @Override
    public Pagination<Category> findAll(SearchQuery query) {
        final var page = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.by(Sort.Direction.fromString(query.direction()), query.sort())
        );

        final var pageResult = repository.findAll(SpecificationUtils.<CategoryJpa>flike("name", query.terms()), page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(CategoryJpa::toDomain).toList()
        );
    }
}
