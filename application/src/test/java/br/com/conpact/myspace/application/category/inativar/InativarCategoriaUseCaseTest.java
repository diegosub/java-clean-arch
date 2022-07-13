package br.com.conpact.myspace.application.category.inativar;

import br.com.conpact.myspace.application.category.UseCaseTest;
import br.com.conpact.myspace.application.category.usecase.inactivate.DeactivateCategoryUseCaseImpl;
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

public class InativarCategoriaUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DeactivateCategoryUseCaseImpl useCase;

    @Mock
    private CategoryGateway gateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(gateway);
    }

    // 1. Caminho feliz
    // 2. Inativar com id inexistente

    @Test
    public void givenAValidId_whenCallDeactivate_thenReturnInactiveCategory() {
        final var id = "0e5b1d59-80e7-4d31-958a-a163065ddb5d";
        final var activeCategory = Category.create("Lazer");
        final var inactiveCategory = Category.create("Lazer");
        inactiveCategory.deactivate();

        Assertions.assertNotNull(activeCategory);
        Assertions.assertEquals(activeCategory.isActive(), true);

        when(gateway.deactivate(id)).thenReturn(inactiveCategory);

        final var categoryOutput = useCase.execute(id);

        Assertions.assertNotNull(categoryOutput);
        Assertions.assertEquals(categoryOutput.getName(), inactiveCategory.getName());
        Assertions.assertEquals(categoryOutput.isActive(), false);
        Assertions.assertEquals(categoryOutput.getCreatedAt(), inactiveCategory.getCreatedAt());
        Assertions.assertEquals(categoryOutput.getUpdatedAt(), inactiveCategory.getUpdatedAt());
        Assertions.assertNotNull(categoryOutput.getDeletedAt());

        Mockito.verify(gateway, times(1)).deactivate(any());
    }

    @Test
    public void givenANotExistsId_whenCallDeactivate_thenReturnADomainException() {
        final String id = "0e5b1d59-80e7-4d31-958a-a163065ddb53";
        final var exception = DomainException.with("Não foi possível encontrar a categoria");

        when(gateway.deactivate(id)).thenThrow(DomainException.with("Não foi possível encontrar a categoria"));

        final var exceptionRetornada = Assertions.assertThrows(DomainException.class, () -> useCase.execute(id));

        Assertions.assertEquals(exceptionRetornada.getMessage(), exception.getMessage());
        Assertions.assertEquals(exceptionRetornada.getErrors(), exception.getErrors());

        Mockito.verify(gateway, times(1)).deactivate(any());
    }
}
