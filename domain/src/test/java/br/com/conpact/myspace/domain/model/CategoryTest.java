package br.com.conpact.myspace.domain.model;

import br.com.conpact.myspace.domain.category.Category;
import br.com.conpact.myspace.domain.exception.DomainException;
import br.com.conpact.myspace.domain.validation.handler.ValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    public void givenValidParams_whenCreateACategory_thenReturnAValidCategory() {
        final var name = "Alimentação";

        var category = Category.create(name);
        category.validate(new ValidationHandler());

        Assertions.assertNotNull(category);
        Assertions.assertNotNull(category.getId());

        Assertions.assertEquals(name, category.getName());
        Assertions.assertTrue(category.isActive());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());
        Assertions.assertNull(category.getDeletedAt());
    }

    @Test
    public void givenAnInvalidNullName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String name = null;
        final var messageError = "'nome' não pode ser null";
        final var messageCount = 1;

        var category = Category.create(name);

        var exception = Assertions.assertThrows(DomainException.class, () -> category.validate(new ValidationHandler()));

        Assertions.assertEquals(messageError, exception.getErrors().get(0));
        Assertions.assertEquals(messageCount, exception.getErrors().size());
    }

    @Test
    public void givenAnInvalidEmptyName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String name = " ";
        final var messageErrorEmpty = "'nome' não pode ser empty";
        final var messageErrorLength = "'nome' deve conter entre 3 e 255 caracteres";
        final var messageCount = 2;

        var category = Category.create(name);

        var exception = Assertions.assertThrows(DomainException.class, () -> category.validate(new ValidationHandler()));

        Assertions.assertEquals(messageErrorEmpty, exception.getErrors().get(0));
        Assertions.assertEquals(messageErrorLength, exception.getErrors().get(1));
        Assertions.assertEquals(messageCount, exception.getErrors().size());
    }

    @Test
    public void givenAnInvalidNameLengthLessThan3_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String name = "di ";
        final var messageError = "'nome' deve conter entre 3 e 255 caracteres";
        final var messageCount = 1;

        var category = Category.create(name);

        var exception = Assertions.assertThrows(DomainException.class, () -> category.validate(new ValidationHandler()));

        Assertions.assertEquals(messageError, exception.getErrors().get(0));
        Assertions.assertEquals(messageCount, exception.getErrors().size());
    }

    @Test
    public void givenAnInvalidNameLengthMoreThan255_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String name = """
                Gostaria de enfatizar que o entendimento das metas propostas deve passar por modificações independentemente dos procedimentos 
                normalmente adotados. Percebemos, cada vez mais, que o novo modelo estrutural aqui preconizado garante a contribuição de um grupo 
                importante na determinação das novas proposições. Todas estas questões
                """;
        final var messageError = "'nome' deve conter entre 3 e 255 caracteres";
        final var messageCount = 1;

        var category = Category.create(name);

        var exception = Assertions.assertThrows(DomainException.class, () -> category.validate(new ValidationHandler()));

        Assertions.assertEquals(messageError, exception.getErrors().get(0));
        Assertions.assertEquals(messageCount, exception.getErrors().size());
    }

    @Test
    public void givenAValidCategory_whenCallDeactivate_thenReturnCategoryUpdated() {
        final var name = "Alimentação";

        var category = Category.create(name);

        Assertions.assertNotNull(category);
        Assertions.assertNotNull(category.getId());

        Assertions.assertEquals(name, category.getName());
        Assertions.assertTrue(category.isActive());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());
        Assertions.assertNull(category.getDeletedAt());

        category.deactivate();

        Assertions.assertNotNull(category);
        Assertions.assertNotNull(category.getId());

        Assertions.assertEquals(name, category.getName());
        Assertions.assertFalse(category.isActive());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());
        Assertions.assertNotNull(category.getDeletedAt());
    }

    @Test
    public void givenAInactiveCategory_whenCallActivate_thenReturnCategoryUpdated() {
        final var name = "Alimentação";

        var category = Category.create(name);

        Assertions.assertNotNull(category);
        Assertions.assertNotNull(category.getId());

        Assertions.assertEquals(name, category.getName());
        Assertions.assertTrue(category.isActive());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());
        Assertions.assertNull(category.getDeletedAt());

        category.deactivate();

        Assertions.assertNotNull(category);
        Assertions.assertNotNull(category.getId());

        Assertions.assertEquals(name, category.getName());
        Assertions.assertFalse(category.isActive());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());
        Assertions.assertNotNull(category.getDeletedAt());

        category.activate();

        Assertions.assertNotNull(category);
        Assertions.assertNotNull(category.getId());

        Assertions.assertEquals(name, category.getName());
        Assertions.assertTrue(category.isActive());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());
        Assertions.assertNull(category.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallUpdate_thenReturnCategoryUpdated() {
        final var name = "Alimentação";
        final var newName = "Viagens";

        var category = Category.create(name);
        category.validate(new ValidationHandler());

        Assertions.assertNotNull(category);
        Assertions.assertNotNull(category.getId());

        var actualCategory = category.update(newName);
        actualCategory.validate(new ValidationHandler());

        Assertions.assertNotNull(actualCategory);
        Assertions.assertEquals(category.getId(), actualCategory.getId());

        Assertions.assertEquals(newName, actualCategory.getName());
        Assertions.assertTrue(actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }
}
