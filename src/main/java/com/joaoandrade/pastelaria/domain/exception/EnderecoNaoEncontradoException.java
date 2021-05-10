package com.joaoandrade.pastelaria.domain.exception;

public class EnderecoNaoEncontradoException extends ObjetoNaoEncontradoException {

	private static final long serialVersionUID = 1L;

	public EnderecoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public EnderecoNaoEncontradoException(Long id) {
		super(String.format("O Endereço de id %d não foi encontrado no sistema!", id));
	}

}
