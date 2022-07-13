package br.com.conpact.myspace.application.category.inserir;

import br.com.conpact.myspace.application.category.UseCaseTest;
import br.com.conpact.myspace.application.category.usecase.insert.InsertCategoryUseCaseImpl;
import br.com.conpact.myspace.domain.category.Category;
import br.com.conpact.myspace.domain.category.CategoryGateway;
import br.com.conpact.myspace.domain.exception.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class InserirCategoriaUseCaseTest extends UseCaseTest {

    @InjectMocks
    private InsertCategoryUseCaseImpl useCase;

    @Mock
    private CategoryGateway gateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(gateway);
    }

    // 1. Teste do caminho feliz
    // 2. Teste passando um nome null
    // 3. Teste passando um nome empty
    // 4. Teste simulando um erro generico vindo do gateway

    @Test
    public void givenAValidParam_whenCallInsert_thenReturnAValidCategory() {
        final var expectedName = "Lazer";
        final var category = Category.create(expectedName);

        when(gateway.insert(any())).thenAnswer(returnsFirstArg());
        var output = useCase.execute(category);

        Assertions.assertNotNull(output);
        Assertions.assertNotNull(output.getId());
        Assertions.assertEquals(expectedName, output.getName());
        Assertions.assertEquals(true, output.isActive());
        Assertions.assertNotNull(output.getCreatedAt());
        Assertions.assertNotNull(output.getUpdatedAt());
        Assertions.assertNull(output.getDeletedAt());

        Mockito.verify(gateway, times(1)).insert(any());
    }

    @Test
    public void givenANullParam_whenCallInsert_thenReturnADomainException() {
        final String expectedName = null;
        final var errorExpectedName = "'nome' não pode ser null";
        final var errorExpectedMessage = "Erro na validação da categoria";
        final var expectedErrorCount = 1;

        final var category = Category.create(expectedName);

        final var exception = Assertions.assertThrows(DomainException.class, () -> useCase.execute(category));

        Assertions.assertEquals(expectedErrorCount, exception.getErrors().size());
        Assertions.assertEquals(errorExpectedName, exception.getErrors().get(0));
        Assertions.assertEquals(errorExpectedMessage, exception.getMessage());

        Mockito.verify(gateway, times(0)).insert(any());
    }

    @Test
    public void givenEmptyParam_whenCallInsert_thenReturnDomainException() {
        final String expectedName = "";
        final var errorNameEmptyExpected = "'nome' não pode ser empty";
        final var errorNameSizeExpected = "'nome' deve conter entre 3 e 255 caracteres";
        final var erroMessageExpected = "Erro na validação da categoria";
        final var expectedErrorCount = 2;

        final var category = Category.create(expectedName);

        final var exception = Assertions.assertThrows(DomainException.class, () -> useCase.execute(category));

        Assertions.assertEquals(expectedErrorCount, exception.getErrors().size());
        Assertions.assertEquals(errorNameEmptyExpected, exception.getErrors().get(0));
        Assertions.assertEquals(errorNameSizeExpected, exception.getErrors().get(1));
        Assertions.assertEquals(erroMessageExpected, exception.getMessage());

        Mockito.verify(gateway, times(0)).insert(any());
    }

    @Test
    public void givenAValidParam_whenRepositoryThrowsRandomException_thenReturnException() {
        final var edxpectedName = "Viagens";
        final var expectedErrorMessage = "Repository error";

        final var category = Category.create(edxpectedName);

        when(gateway.insert(any()))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var exception = Assertions.assertThrows(IllegalStateException.class, ()-> useCase.execute(category));

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(gateway, times(1)).insert(any());
    }

}
