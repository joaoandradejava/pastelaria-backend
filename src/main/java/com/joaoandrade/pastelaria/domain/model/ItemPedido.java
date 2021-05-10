package com.joaoandrade.pastelaria.domain.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class ItemPedido {

	@EmbeddedId
	private ItemPedidoPk id;
	private Integer quantidade;
	private Double valor;
	private Integer desconto;

	public ItemPedido() {
	}

	public ItemPedido(ItemPedidoPk id, Integer quantidade, Double valor, Integer desconto) {
		this.id = id;
		this.quantidade = quantidade;
		this.valor = valor;
		this.desconto = desconto;
	}

	public ItemPedidoPk getId() {
		return id;
	}

	public void setId(ItemPedidoPk id) {
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

	public double calcularValorTotalDoItemPedido() {
		double desconto = this.desconto / 100.0;

		return (this.valor * this.quantidade) * (1 - desconto);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemPedido other = (ItemPedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
