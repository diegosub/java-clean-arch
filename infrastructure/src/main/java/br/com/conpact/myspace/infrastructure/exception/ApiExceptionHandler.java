package br.com.conpact.myspace.infrastructure.exception;

import br.com.conpact.myspace.domain.exception.DomainException;
import br.com.conpact.myspace.domain.exception.NotFoundException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se "
            + "o problema persistir, entre em contato com um administrador.";

    public static final String MSG_ERRO_AUTH_BAD_CREDENTIALS = "Suas credenciais não conferem. Favor verificar seu login e senha";

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
            HttpMediaTypeNotAcceptableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        return ResponseEntity.status(status).headers(headers).build();
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        ErrorType erroType = ErrorType.RECURSO_NAO_ENCONTRADO;
        String detail = String.format("O recurso %s, que você tentou acessar, é inexistente.", ex.getRequestURL());

        Error erro = createProblemBuilder(status, erroType).message(detail).build();

        return handleExceptionInternal(ex, erro, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorType erroType = ErrorType.PARAMETRO_INVALIDO;

        String detail = String.format(
                "O parâmetro de URL '%s' recebeu o valor '%s', "
                        + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        Error erro = createProblemBuilder(status, erroType).message(detail).build();

        return handleExceptionInternal(ex, erro, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
        }

        ErrorType problemType = ErrorType.MENSAGEM_INCOMPREENSIVEL;
        String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";

        Error erro = createProblemBuilder(status, problemType).message(detail)
                .build();

        return handleExceptionInternal(ex, erro, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String path = joinPath(ex.getPath());

        ErrorType problemType = ErrorType.MENSAGEM_INCOMPREENSIVEL;
        String detail = String
                .format("A propriedade '%s' não existe. " + "Corrija ou remova essa propriedade e tente novamente.", path);

        Error erro = createProblemBuilder(status, problemType).message(detail)
                .build();

        return handleExceptionInternal(ex, erro, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String path = joinPath(ex.getPath());

        ErrorType erroType = ErrorType.MENSAGEM_INCOMPREENSIVEL;
        String detail = String.format(
                "A propriedade '%s' recebeu o valor '%s', "
                        + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                path, ex.getValue(), ex.getTargetType().getSimpleName());

        Error erro = createProblemBuilder(status, erroType).message(detail).build();

        return handleExceptionInternal(ex, erro, headers, status, request);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFound(NotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorType erroType = ErrorType.RECURSO_NAO_ENCONTRADO;
        String detail = ex.getMessage();

        Error erro = createProblemBuilder(status, erroType).message(detail).build();

        return handleExceptionInternal(ex, erro, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<?> handleDomain(DomainException ex, WebRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ErrorType erroType = ErrorType.ERRO_NEGOCIO;
        String detail = ex.getMessage();

        Error erro = createProblemBuilder(status, erroType).message(detail).errors(ex.getErrors()).build();

        return handleExceptionInternal(ex, erro, new HttpHeaders(), status, request);
    }

//    @ExceptionHandler(AutenticacaoException.class)
//    public ResponseEntity<?> handleAutenticacao(AutenticacaoException ex, WebRequest request) {
//        HttpStatus status = HttpStatus.UNAUTHORIZED;
//        ErroType erroType = ErroType.ERRO_AUTENTICACAO;
//        String detail = ex.getMessage();
//
//        Erro erro = createProblemBuilder(status, erroType, detail).userMessage(detail).build();
//
//        return handleExceptionInternal(ex, erro, new HttpHeaders(), status, request);
//    }

//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<Object> handleLogin(BadCredentialsException ex, WebRequest request) {
//        HttpStatus status = HttpStatus.UNAUTHORIZED;
//        ErroType erroType = ErroType.ERRO_AUTENTICACAO;
//        String detail = MSG_ERRO_AUTH_BAD_CREDENTIALS;
//
//        Erro erro = createProblemBuilder(status, erroType, detail).userMessage(detail).build();
//
//        return handleExceptionInternal(ex, erro, new HttpHeaders(), status, request);
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorType erroType = ErrorType.ERRO_DE_SISTEMA;
        String detail = MSG_ERRO_GENERICA_USUARIO_FINAL;

        Error erro = createProblemBuilder(status, erroType).message(detail).build();

        return handleExceptionInternal(ex, erro, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (body == null) {
            body = Error.builder().timestamp(Instant.now()).title(status.getReasonPhrase()).status(status.value())
                    .message(MSG_ERRO_GENERICA_USUARIO_FINAL).build();
        } else if (body instanceof String) {
            body = Error.builder().timestamp(Instant.now()).title((String) body).status(status.value())
                    .message(MSG_ERRO_GENERICA_USUARIO_FINAL).build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Error.ErrorBuilder createProblemBuilder(HttpStatus status, ErrorType problemType) {
        return Error.builder().timestamp(Instant.now()).status(status.value()).type(problemType.getUri())
                .title(problemType.getTitle());
    }

    private String joinPath(List<Reference> references) {
        return references.stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
    }
}