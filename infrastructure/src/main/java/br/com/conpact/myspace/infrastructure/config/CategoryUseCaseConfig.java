package br.com.conpact.myspace.infrastructure.config;

import br.com.conpact.myspace.application.category.usecase.find.get.GetByIdCategoryUseCase;
import br.com.conpact.myspace.application.category.usecase.find.get.GetByIdCategoryUseCaseImpl;
import br.com.conpact.myspace.application.category.usecase.find.list.ListCategoryUseCase;
import br.com.conpact.myspace.application.category.usecase.find.list.ListCategoryUseCaseImpl;
import br.com.conpact.myspace.application.category.usecase.insert.InsertCategoryUseCase;
import br.com.conpact.myspace.application.category.usecase.insert.InsertCategoryUseCaseImpl;
import br.com.conpact.myspace.application.category.usecase.update.UpdateCategoryUseCase;
import br.com.conpact.myspace.application.category.usecase.update.UpdateCategoryUseCaseImpl;
import br.com.conpact.myspace.domain.category.CategoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryUseCaseConfig {

    private final CategoryGateway categoryGateway;

    public CategoryUseCaseConfig(final CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Bean
    public InsertCategoryUseCase insertCategoryUseCase() {
        return new InsertCategoryUseCaseImpl(categoryGateway);
    }

    @Bean
    public UpdateCategoryUseCase updateCategoryUseCase() {
        return new UpdateCategoryUseCaseImpl(categoryGateway);
    }

    @Bean
    public GetByIdCategoryUseCase getByIdCategoryUseCase() {
        return new GetByIdCategoryUseCaseImpl(categoryGateway);
    }

    @Bean
    public ListCategoryUseCase listCategoryUseCase() {
        return new ListCategoryUseCaseImpl(categoryGateway);
    }

}
