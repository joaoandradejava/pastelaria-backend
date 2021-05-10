package com.joaoandrade.pastelaria.api.model;

public class ItemPedidoPkModel {
	private ProdutoResumoModel produto;

	public ItemPedidoPkModel() {
	}

	public ProdutoResumoModel getProduto() {
		return produto;
	}

	public void setProduto(ProdutoResumoModel produto) {
		this.produto = produto;
	}

}
