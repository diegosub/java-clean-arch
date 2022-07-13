package br.com.conpact.myspace.infrastructure.enums;

public enum ErrorTypeEnum {
    NOT_FOUND("/not-found", "NotFound"),
    INTERNAL_SERVER_ERROR("/internal-server-error", "InternalServerError"),
    UNPROCESSABLE_ENTITY("/unprocessable-entity", "UnprocessableEntity"),


    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
    ERRO_AUTENTICACAO("/erro-autenticacao", "Erro de autenticação");

    ErrorTypeEnum(String path, String title) {
        this.uri = "http://localhost:8080" + path;
        this.title = title;
    }

    private String title;

    private String uri;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
