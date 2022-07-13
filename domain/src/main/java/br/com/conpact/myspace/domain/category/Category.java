package br.com.conpact.myspace.domain.category;

import br.com.conpact.myspace.domain.validation.handler.ValidationHandler;

import java.time.Instant;
import java.util.UUID;

public class Category {

    private String id;
    private String name;
    private Boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Category(
            final String id,
            final String name,
            final Boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Category create(final String id, final String name) {
        final var now = Instant.now();
        return new Category(id, name, true, now, now, null);
    }

    public static Category create(final String name) {
        final var id = UUID.randomUUID().toString();
        final var now = Instant.now();
        return new Category(id, name, true, now, now, null);
    }

    public Category update(final String name) {
        this.name = name;
        this.updatedAt = Instant.now();
        return this;
    }

    public void activate() {
        if(!this.isActive()) {
            this.active = true;
            this.updatedAt = Instant.now();
            this.deletedAt = null;
        }
    }

    public void deactivate() {
        if(this.isActive()) {
            this.active = false;
            this.deletedAt = Instant.now();
        }
    }

    public static Category with(
            final String id,
            final String name,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        return new Category(id, name, active, createdAt, updatedAt, deletedAt);
    }

    public void validate(final ValidationHandler handler) {
        new CategoryValidator(this, handler).validate();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }
}

