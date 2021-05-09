package com.joaoandrade.pastelaria.domain.model.enumeration;

public enum Funcao {
	ADMIN("ROLE_ADMIN"), CLIENTE("ROLE_CLIENTE");

	private String descricao;

	private Funcao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
