package br.com.conpact.myspace.infrastructure.category.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateCategoryRequest(
        @JsonProperty("name") String name
) {
}
