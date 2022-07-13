package br.com.conpact.myspace.application.category.ativar;

import br.com.conpact.myspace.application.category.UseCaseTest;
import br.com.conpact.myspace.application.category.usecase.activate.ActivateCategoryUseCaseImpl;
import br.com.conpact.myspace.domain.category.Category;
import br.com.conpact.myspace.domain.category.CategoryGateway;
import br.com.conpact.myspace.domain.exception.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class AtivarCategoriaUseCaseTest extends UseCaseTest {

    @InjectMocks
    private ActivateCategoryUseCaseImpl useCase;

    @Mock
    private CategoryGateway gateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(gateway);
    }

    // 1. Caminho feliz
    // 2. Ativar com id inexistente

    @Test
    public void givenAValidId_whenCallActivate_thenReturnAActiveCategory() {
        final var id = "0e5b1d59-80e7-4d31-958a-a163065ddb5d";
        final var inactiveCategory = Category.create("Lazer");
        inactiveCategory.deactivate();
        final var activeCategory = Category.create("Lazer");

        Assertions.assertNotNull(inactiveCategory);
        Assertions.assertEquals(inactiveCategory.isActive(), false);

        when(gateway.activate(id)).thenReturn(activeCategory);

        final var output = useCase.execute(id);

        Assertions.assertNotNull(output);
        Assertions.assertEquals(output.getName(), activeCategory.getName());
        Assertions.assertEquals(output.isActive(), activeCategory.isActive());
        Assertions.assertEquals(output.getCreatedAt(), activeCategory.getCreatedAt());
        Assertions.assertEquals(output.getUpdatedAt(), activeCategory.getUpdatedAt());
        Assertions.assertEquals(output.getDeletedAt(), activeCategory.getDeletedAt());

        Mockito.verify(gateway, times(1)).activate(any());
    }

    @Test
    public void givenANotExistsId_whenCallActivate_thenReturnADomainException() {
        final String id = "0e5b1d59-80e7-4d31-958a-a163065ddb53";
        final var exception = DomainException.with("Não foi possível encontrar a categoria");

        when(gateway.activate(id)).thenThrow(DomainException.with("Não foi possível encontrar a categoria"));

        final var exceptionRetrun = Assertions.assertThrows(DomainException.class, () -> useCase.execute(id));

        Assertions.assertEquals(exceptionRetrun.getMessage(), exception.getMessage());
        Assertions.assertEquals(exceptionRetrun.getErrors(), exception.getErrors());

        Mockito.verify(gateway, times(1)).activate(any());
    }
}
