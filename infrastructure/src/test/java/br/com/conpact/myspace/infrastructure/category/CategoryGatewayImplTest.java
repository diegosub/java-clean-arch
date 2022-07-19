package br.com.conpact.myspace.infrastructure.category;

import br.com.conpact.myspace.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
@ComponentScan(
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".[MySQLGateway]")
        }
)
public class CategoryGatewayImplTest {

    @Autowired
    private CategoryGatewayImpl categoryGatewayImpl;

    @Autowired
    private CategoryRepository repository;

    @Test
    public void inject(){
        Assertions.assertNotNull(categoryGatewayImpl);
        Assertions.assertNotNull(repository);
    }
}
