package com.joaoandrade.pastelaria.api.model;

public class ItemPedidoModel {
	private ItemPedidoPkModel id;
	private Integer quantidade;
	private Double valor;
	private Integer desconto;

	public ItemPedidoModel() {
	}

	public ItemPedidoPkModel getId() {
		return id;
	}

	public void setId(ItemPedidoPkModel id) {
		this.id = id;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Integer getDesconto() {
		return desconto;
	}

	public void setDesconto(Integer desconto) {
		this.desconto = desconto;
	}

}
