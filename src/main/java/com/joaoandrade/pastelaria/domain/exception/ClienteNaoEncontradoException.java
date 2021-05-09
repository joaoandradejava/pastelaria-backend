package com.joaoandrade.pastelaria.domain.exception;

public class ClienteNaoEncontradoException extends ObjetoNaoEncontradoException {

	private static final long serialVersionUID = 1L;

	public ClienteNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public ClienteNaoEncontradoException(Long id) {
		super(String.format("O Cliente de id %d não foi encontrado no sistema!", id));
	}

}
