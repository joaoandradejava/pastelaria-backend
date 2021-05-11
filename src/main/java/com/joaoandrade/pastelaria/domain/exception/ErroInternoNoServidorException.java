package com.joaoandrade.pastelaria.domain.exception;

public class ErroInternoNoServidorException extends NegocioException {

	private static final long serialVersionUID = 1L;

	public ErroInternoNoServidorException(String mensagem) {
		super(mensagem);
	}

}
