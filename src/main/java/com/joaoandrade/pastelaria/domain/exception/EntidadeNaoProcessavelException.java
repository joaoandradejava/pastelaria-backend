package com.joaoandrade.pastelaria.domain.exception;

public class EntidadeNaoProcessavelException extends NegocioException {

	private static final long serialVersionUID = 1L;

	public EntidadeNaoProcessavelException(String mensagem) {
		super(mensagem);
	}

}
