package br.com.conpact.myspace.infrastructure.category.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryRepository extends JpaRepository<CategoryJpa, String>, JpaSpecificationExecutor<CategoryJpa> {
}
