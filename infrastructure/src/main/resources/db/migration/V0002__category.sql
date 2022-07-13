CREATE TABLE main.category (
    id VARCHAR(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    deleted_at timestamp without time zone NULL,

    CONSTRAINT pk_usuario PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS main.category
    OWNER to postgres;