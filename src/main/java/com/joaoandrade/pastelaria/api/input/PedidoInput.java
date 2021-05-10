package com.joaoandrade.pastelaria.api.input;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PedidoInput {

	@Valid
	@NotEmpty
	private List<ItemPedidoInput> itens = new ArrayList<>();

	@Valid
	@NotNull
	private EnderecoIdInput enderecoDeEntrega;

	public PedidoInput() {
	}

	public List<ItemPedidoInput> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedidoInput> itens) {
		this.itens = itens;
	}

	public EnderecoIdInput getEnderecoDeEntrega() {
		return enderecoDeEntrega;
	}

	public void setEnderecoDeEntrega(EnderecoIdInput enderecoDeEntrega) {
		this.enderecoDeEntrega = enderecoDeEntrega;
	}

}
