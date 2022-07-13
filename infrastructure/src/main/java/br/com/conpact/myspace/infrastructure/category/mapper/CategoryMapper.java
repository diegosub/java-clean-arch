package br.com.conpact.myspace.infrastructure.category.mapper;

import br.com.conpact.myspace.domain.category.Category;
import br.com.conpact.myspace.infrastructure.category.model.CategoryResponse;

public class CategoryMapper {

    public static CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.isActive(),
                category.getCreatedAt(),
                category.getUpdatedAt(),
                category.getDeletedAt()
        );
    }

}
