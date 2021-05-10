package com.joaoandrade.pastelaria.domain.exception;

public class ProdutoNaoEncontradoException extends ObjetoNaoEncontradoException {

	private static final long serialVersionUID = 1L;

	public ProdutoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public ProdutoNaoEncontradoException(Long id) {
		super(String.format("O Produto de id %d não foi encontrado no sistema!", id));
	}

}
