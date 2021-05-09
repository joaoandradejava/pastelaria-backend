package com.joaoandrade.pastelaria.domain.exception;

public class ObjetoNaoEncontradoException extends NegocioException {

	private static final long serialVersionUID = 1L;

	public ObjetoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

}
