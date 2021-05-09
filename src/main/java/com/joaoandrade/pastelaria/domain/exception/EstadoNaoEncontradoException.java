package com.joaoandrade.pastelaria.domain.exception;

public class EstadoNaoEncontradoException extends ObjetoNaoEncontradoException {

	private static final long serialVersionUID = 1L;

	public EstadoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public EstadoNaoEncontradoException(Long id) {
		super(String.format("O Estado de id %d não foi encontrado no sistema!", id));
	}

}
