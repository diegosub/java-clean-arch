package br.com.conpact.myspace.application.category.pesquisar.get;

import br.com.conpact.myspace.application.category.UseCaseTest;
import br.com.conpact.myspace.application.category.usecase.find.get.GetByIdCategoryUseCaseImpl;
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

public class GetByIdCategoryUseCaseTest extends UseCaseTest {

    @InjectMocks
    private GetByIdCategoryUseCaseImpl useCase;

    @Mock
    private CategoryGateway gateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(gateway);
    }

    // 1. Caminho feliz
    // 2. Id Invalido
    // 3. Id null
    // 4. Error repository

    @Test
    public void givenIdValid_whenCallExecute_thenReturnCategoryValid() {
        final var expectedCategory = Category.create("Lazer");
        final var id = "0e5b1d59-80e7-4d31-958a-a163065ddb5d";

        when(gateway.getById(eq(id))).thenReturn(Optional.of(expectedCategory));

        final var output = useCase.execute(id);

        Assertions.assertNotNull(output);
        Assertions.assertNotNull(output.getId());
        Assertions.assertEquals(output.getName(), expectedCategory.getName());
        Assertions.assertEquals(output.isActive(), true);
        Assertions.assertNotNull(output.getCreatedAt());
        Assertions.assertNotNull(output.getUpdatedAt());
        Assertions.assertNull(output.getDeletedAt());

        Mockito.verify(gateway, times(1)).getById(any());
    }

    @Test
    public void givenIdInvalid_whenCallExecute_thenReturnNotFoundException() {
        final var expectedException = NotFoundException.with("Entidade não encontrada");
        final var id = "fake id";

        when(gateway.getById(eq(id))).thenThrow(DomainException.with("Entidade não encontrada"));
        final var exception = Assertions.assertThrows(DomainException.class, () -> useCase.execute(id));

        Assertions.assertEquals(expectedException.getMessage(), exception.getMessage());

        Mockito.verify(gateway, times(1)).getById(any());
    }

    @Test
    public void givenIdNull_whenCallExecute_thenReturnNotFoundException() {
        final var expectedException = DomainException.with("Entidade não encontrada");
        final String id = null;

        when(gateway.getById(id)).thenThrow(DomainException.with("Entidade não encontrada"));
        final var exception = Assertions.assertThrows(DomainException.class, () -> useCase.execute(id));

        Assertions.assertEquals(expectedException.getMessage(), exception.getMessage());
        Assertions.assertEquals(expectedException.getErrors(), exception.getErrors());

        Mockito.verify(gateway, times(1)).getById(any());
    }

    @Test
    public void givenValidParams_whenRepositoryThrowsRandomException_thenReturnAException() {
        final var expectedErrorMessage = "Repository error";

        when(gateway.getById(any()))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var exception = Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(any()));

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(gateway, times(1)).getById(any());
    }
}
