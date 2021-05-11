package com.joaoandrade.pastelaria.domain.model.enumeration;

public enum SituacaoPedido {
	CANCELADO("Cancelado"), SAIU_PARA_ENTREGA("Saiu Para Entrega"), CONCLUIDO("Concluido"),
	FAZENDO_A_COMIDA("Fazendo a Comida");

	private String descricao;

	private SituacaoPedido(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
