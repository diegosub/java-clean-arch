package br.com.conpact.myspace.domain.category;

import br.com.conpact.myspace.domain.exception.DomainException;
import br.com.conpact.myspace.domain.validation.Validator;
import br.com.conpact.myspace.domain.validation.handler.ValidationHandler;

public class CategoryValidator extends Validator {

    private final Category category;
    private final int MIN_NOME_LENGTH = 3;
    private final int MAX_NOME_LENGTH = 255;

    private final String MSG_ERRO_VALIDACAO = "Erro na validação da categoria";

    protected CategoryValidator(final Category category, final ValidationHandler handler) {
        super(handler);
        this.category = category;
    }

    @Override
    public void validate() {
        validateName();
    }

    private void validateName() {
        final var name = this.category.getName();

        if(name == null) {
            this.validationHandler().append("'nome' não pode ser null");
        }

        if(name != null && name.trim().isEmpty()) {
            this.validationHandler().append("'nome' não pode ser empty");
        }

        if(name != null && (name.trim().length() > MAX_NOME_LENGTH || name.trim().length() < MIN_NOME_LENGTH)) {
            this.validationHandler().append("'nome' deve conter entre 3 e 255 caracteres");
        }

        if(!this.validationHandler().getErrors().isEmpty()) {
            throw DomainException.with(MSG_ERRO_VALIDACAO, this.validationHandler().getErrors());
        }
    }
}
