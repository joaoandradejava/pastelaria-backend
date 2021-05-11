package com.joaoandrade.pastelaria.api.exceptionhandler;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.joaoandrade.pastelaria.domain.exception.AcessoNegadoException;
import com.joaoandrade.pastelaria.domain.exception.EntidadeEmUsoException;
import com.joaoandrade.pastelaria.domain.exception.EntidadeNaoProcessavelException;
import com.joaoandrade.pastelaria.domain.exception.ErroInternoNoServidorException;
import com.joaoandrade.pastelaria.domain.exception.NegocioException;
import com.joaoandrade.pastelaria.domain.exception.ObjetoNaoEncontradoException;
import com.joaoandrade.pastelaria.domain.exception.PrepararMimeMessageException;

@ControllerAdvice
public class ResourceHandler extends ResponseEntityExceptionHandler {

	private static final String STANDARD_MESSAGE = "Ocorreu um erro inesperado no sistema, se o problema persistir recomendo que entre em contato com o desenvolvedor da API.";

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleExceptionGenerico(Exception ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		Error error = Error.ERRO_INTERNO_NO_SERVIDOR;
		String message = "Ocorreu um erro inesperado no servidor(backend), se o problema persistir recomendo que entre em contato com o desenvolvedor da API.";
		ProblemDetail problemDetail = new ProblemDetail(error.getType(), error.getTitle(), status.value(), message,
				STANDARD_MESSAGE);

		return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(ErroInternoNoServidorException.class)
	public ResponseEntity<Object> handleErroInternoNoServidor(ErroInternoNoServidorException ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		Error error = Error.ERRO_INTERNO_NO_SERVIDOR;
		String message = ex.getMessage();
		ProblemDetail problemDetail = new ProblemDetail(error.getType(), error.getTitle(), status.value(), message,
				STANDARD_MESSAGE);

		return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleAccessDenied(AccessDeniedException ex, WebRequest request) {
		HttpStatus status = HttpStatus.FORBIDDEN;
		Error error = Error.ACESSO_NEGADO;
		String message = "Acesso negado!";
		ProblemDetail problemDetail = new ProblemDetail(error.getType(), error.getTitle(), status.value(), message,
				message);

		return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(PrepararMimeMessageException.class)
	public ResponseEntity<Object> handlePrepararMimeMessage(PrepararMimeMessageException ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		Error error = Error.ERRO_INTERNO_NO_SERVIDOR;
		String message = ex.getMessage();
		ProblemDetail problemDetail = new ProblemDetail(error.getType(), error.getTitle(), status.value(), message,
				STANDARD_MESSAGE);

		return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(AcessoNegadoException.class)
	public ResponseEntity<Object> handleAcessoNegado(AcessoNegadoException ex, WebRequest request) {
		HttpStatus status = HttpStatus.FORBIDDEN;
		Error error = Error.ACESSO_NEGADO;
		String message = ex.getMessage();
		ProblemDetail problemDetail = new ProblemDetail(error.getType(), error.getTitle(), status.value(), message,
				message);

		return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Error error = Error.NEGOCIO;
		String message = ex.getMessage();
		ProblemDetail problemDetail = new ProblemDetail(error.getType(), error.getTitle(), status.value(), message,
				message);

		return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(ObjetoNaoEncontradoException.class)
	public ResponseEntity<Object> handleObjetoNaoEncontrado(ObjetoNaoEncontradoException ex, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		Error error = Error.RECURSO_NAO_ENCONTRADO;
		String message = ex.getMessage();
		ProblemDetail problemDetail = new ProblemDetail(error.getType(), error.getTitle(), status.value(), message,
				message);

		return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<Object> handleEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;
		Error error = Error.ENTIDADE_EM_USO;
		String message = ex.getMessage();
		ProblemDetail problemDetail = new ProblemDetail(error.getType(), error.getTitle(), status.value(), message,
				message);

		return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntidadeNaoProcessavelException.class)
	public ResponseEntity<Object> handleEntidadeNaoProcessavel(EntidadeNaoProcessavelException ex, WebRequest request) {
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		Error error = Error.ENTIDADE_NAO_PROCESSAVEL;
		String message = ex.getMessage();
		ProblemDetail problemDetail = new ProblemDetail(error.getType(), error.getTitle(), status.value(), message,
				message);

		return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<Object> handleMaxUploadSizeExceeded(MaxUploadSizeExceededException ex, WebRequest request) {
		Throwable cause = ex.getCause().getCause();

		if (cause instanceof FileSizeLimitExceededException) {
			return handleFileSizeLimitExceeded((FileSizeLimitExceededException) cause, request);
		}

		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		Error error = Error.TAMANHO_MAXIMO_UPLOAD_EXCEDIDO;
		String message = "tamanho máximo de upload excedido";
		ProblemDetail problemDetail = new ProblemDetail(error.getType(), error.getTitle(), status.value(), message,
				message);

		return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), status, request);
	}

	private ResponseEntity<Object> handleFileSizeLimitExceeded(FileSizeLimitExceededException ex, WebRequest request) {
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		Error error = Error.TAMANHO_MAXIMO_UPLOAD_EXCEDIDO;
		Double tamanho = 1048576.0;
		Long tamanhoMaximoDoArquivo = Math.round(ex.getPermittedSize() / tamanho);
		String message = String
				.format(String.format("Faça upload de uma imagem menor que %d MB.", tamanhoMaximoDoArquivo));
		ProblemDetail problemDetail = new ProblemDetail(error.getType(), error.getTitle(), status.value(), message,
				message);

		return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Error error = Error.ERRO_DE_VALIDACAO_DOS_DADOS;
		String message = "Os dados vindos do corpo da requisição são invalidos! verifique se todos os campos foram inseridos corretamente!";
		ProblemDetail problemDetail = new ProblemDetail(error.getType(), error.getTitle(), status.value(), message,
				message);

		for (ObjectError objectError : ex.getAllErrors()) {
			String field = objectError.getObjectName();

			if (objectError instanceof FieldError) {
				field = ((FieldError) objectError).getField();
			}

			String userMessage = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
			problemDetail.adicionarError(field, userMessage);
		}

		return handleExceptionInternal(ex, problemDetail, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Throwable cause = ex.getCause();

		if (cause instanceof UnrecognizedPropertyException) {
			return handleUnrecognizedProperty((UnrecognizedPropertyException) cause, headers, status, request);
		}

		Error error = Error.ERRO_NA_DESSERIALIZACAO_JSON;
		String message = "Erro na desserializacao do JSON. Verifique se a sintaxe do JSON foi inserida corretamente no corpo da requisição.";
		ProblemDetail problemDetail = new ProblemDetail(error.getType(), error.getTitle(), status.value(), message,
				STANDARD_MESSAGE);

		return handleExceptionInternal(ex, problemDetail, headers, status, request);
	}

	private ResponseEntity<Object> handleUnrecognizedProperty(UnrecognizedPropertyException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		Error error = Error.PROPRIEDADE_NAO_RECONHECIDA;
		String message = String.format("Campo '%s' não reconhecido", ex.getPropertyName());
		ProblemDetail problemDetail = new ProblemDetail(error.getType(), error.getTitle(), status.value(), message,
				STANDARD_MESSAGE);

		return handleExceptionInternal(ex, problemDetail, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Error error = Error.METODO_NAO_SUPORTADO;
		String message = String.format("Método de requisição '%s' não suportado!", ex.getMethod());
		ProblemDetail problemDetail = new ProblemDetail(error.getType(), error.getTitle(), status.value(), message,
				STANDARD_MESSAGE);

		return handleExceptionInternal(ex, problemDetail, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		Error error = Error.RECURSO_NAO_ENCONTRADO;
		String message = String.format("Não foi encontrado nenhum endpoint para '%s%s'", ex.getHttpMethod(),
				ex.getRequestURL());
		ProblemDetail problemDetail = new ProblemDetail(error.getType(), error.getTitle(), status.value(), message,
				STANDARD_MESSAGE);

		return handleExceptionInternal(ex, problemDetail, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		Error error = Error.TIPO_INCOPATIVEL;
		String message = String.format("Falha ao tentar converter uma %s com o valor '%s' para o tipo necessario %s",
				ex.getValue().getClass().getSimpleName(), ex.getValue(), ex.getRequiredType().getSimpleName());
		ProblemDetail problemDetail = new ProblemDetail(error.getType(), error.getTitle(), status.value(), message,
				STANDARD_MESSAGE);

		return handleExceptionInternal(ex, problemDetail, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (body == null) {
			body = new ProblemDetail(null, null, status.value(), status.getReasonPhrase(), STANDARD_MESSAGE);
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
}
