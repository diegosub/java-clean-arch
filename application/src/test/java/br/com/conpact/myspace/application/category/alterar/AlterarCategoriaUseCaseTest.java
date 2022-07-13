package br.com.conpact.myspace.application.category.alterar;

import br.com.conpact.myspace.application.category.UseCaseTest;
import br.com.conpact.myspace.application.category.usecase.update.UpdateCategoryUseCaseImpl;
import br.com.conpact.myspace.domain.category.Category;
import br.com.conpact.myspace.domain.category.CategoryGateway;
import br.com.conpact.myspace.domain.exception.DomainException;
import br.com.conpact.myspace.domain.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class AlterarCategoriaUseCaseTest extends UseCaseTest {

    @InjectMocks
    private UpdateCategoryUseCaseImpl useCase;

    @Mock
    private CategoryGateway gateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(gateway);
    }

    // 1. Teste do caminho feliz
    // 2. Teste passando uma propriedade inválida (name)
    // 3. Teste simulando um erro generico vindo do gateway
    // 4. Teste atualizar categoria passando ID inválido

    @Test
    public void givenAValidParam_whenCallUpdate_thenReturnAValidCategory() {
        final var category = Category.create("Alimentação");
        final var id = category.getId();

        final var expectedName = "Lazer";

        when(gateway.getById(eq(id))).thenReturn(Optional.of(category));
        when(gateway.update(any())).thenReturn(Category.create(expectedName));

        var output = useCase.execute(category);

        Assertions.assertNotNull(output);
        Assertions.assertNotNull(output.getId());
        Assertions.assertEquals(expectedName, output.getName());
        Assertions.assertEquals(true, output.isActive());
        Assertions.assertNotNull(output.getCreatedAt());
        Assertions.assertNotNull(output.getUpdatedAt());
        Assertions.assertNull(output.getDeletedAt());

        Mockito.verify(gateway, times(1)).getById(eq(id));
        Mockito.verify(gateway, times(1)).update(any());
    }

    @Test
    public void givenANullParam_whenCallUpdate_thenReturnADomainException() {
        final var category = Category.create("Film");
        final var expectedId = category.getId();
        final String expectedName = null;
        final var errorExpectedName = "'nome' não pode ser null";
        final var errorExpectedMessage = "Erro na validação da categoria";
        final var expectedErrorCount = 1;

        var newCategory = Category.create(null);

        when(gateway.getById(any())).thenReturn(Optional.of(category));

        final var exception = Assertions.assertThrows(DomainException.class, () -> useCase.execute(newCategory));

        Assertions.assertEquals(expectedErrorCount, exception.getErrors().size());
        Assertions.assertEquals(errorExpectedName, exception.getErrors().get(0));
        Assertions.assertEquals(errorExpectedMessage, exception.getMessage());

        Mockito.verify(gateway, times(0)).update(any());
    }

    @Test
    public void givenAValidParam_whenRepositoryThrowsRandomException_thenReturnAException() {
        final var category = Category.create("Laz");
        final var expectedId = category.getId();
        final var expectedName = "Lazer";
        final var expectedErrorMessage = "Gateway error";

        when(gateway.getById(eq(expectedId)))
                .thenReturn(Optional.of(category));

        when(gateway.update(any()))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var exception = Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(category));

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(gateway, times(1)).update(any());
    }

    @Test
    public void givenACommandWithInvalidID_whenCallsUpdateCategory_shouldReturnNotFoundException() {
        final var expectedName = "Filmes";
        final var expectedId = "123";
        final var expectedErrorMessage = "Category with ID 123 was not found";

        var category = Category.create(expectedId, expectedName);

        when(gateway.getById(eq(expectedId)))
                .thenReturn(Optional.empty());

        final var actualException =
                Assertions.assertThrows(NotFoundException.class, () -> useCase.execute(category));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

        Mockito.verify(gateway, times(1)).getById(eq(expectedId));

        Mockito.verify(gateway, times(0)).update(any());
    }

}
