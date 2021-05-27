package com.joaoandrade.pastelaria.domain.dto;

public class ProdutoMaisVendidoDTO {

	private final Long id;
	private final String nome;
	private final String totalVendido;

	public ProdutoMaisVendidoDTO(Long id, String nome, Long totalVendido) {
		this.id = id;
		this.nome = nome;
		this.totalVendido = totalVendido + "x";
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getTotalVendido() {
		return totalVendido;
	}

}
