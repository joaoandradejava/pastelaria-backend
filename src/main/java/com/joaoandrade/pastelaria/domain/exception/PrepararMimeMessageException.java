package com.joaoandrade.pastelaria.domain.exception;

public class PrepararMimeMessageException extends NegocioException {

	private static final long serialVersionUID = 1L;

	public PrepararMimeMessageException(String mensagem) {
		super(mensagem);
	}
}
