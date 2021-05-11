package com.joaoandrade.pastelaria.domain.exception;

public class PedidoNaoEncontradoException extends ObjetoNaoEncontradoException {

	private static final long serialVersionUID = 1L;

	public PedidoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public PedidoNaoEncontradoException(Long id) {
		super(String.format("O Pedido de id %d não foi encontrado no sistema!", id));
	}

}
