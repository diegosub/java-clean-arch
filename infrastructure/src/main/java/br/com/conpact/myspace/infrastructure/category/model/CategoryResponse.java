package br.com.conpact.myspace.infrastructure.category.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record CategoryResponse(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("active") Boolean active,
        @JsonProperty("createdAt") Instant createdAt,
        @JsonProperty("updatedAt") Instant updatedAt,
        @JsonProperty("deletedAt") Instant deletedAt
) {
}
