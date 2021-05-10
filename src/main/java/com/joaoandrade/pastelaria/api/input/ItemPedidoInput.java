package com.joaoandrade.pastelaria.api.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ItemPedidoInput {

	@NotNull
	private Long produtoId;

	@Positive
	@NotNull
	private Integer quantidade;

	public ItemPedidoInput() {
	}

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

}
