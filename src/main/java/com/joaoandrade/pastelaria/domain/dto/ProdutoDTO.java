package com.joaoandrade.pastelaria.domain.dto;

import org.springframework.util.StringUtils;

public class ProdutoDTO {
	private final Long id;
	private final String nome;
	private final Double preco;
	private final String categoria;
	private final String isTemDesconto;
	private final String isTemEstoque;
	private final String isTemFoto;

	public ProdutoDTO(Long id, String nome, Double preco, String categoria, Boolean isTemDesconto, Boolean isTemEstoque,
			String isTemFoto) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.categoria = categoria;
		this.isTemDesconto = isTemDesconto ? "Sim" : "Não";
		this.isTemEstoque = isTemEstoque ? "Sim" : "Não";
		this.isTemFoto = StringUtils.hasLength(isTemFoto) ? "Sim" : "Não";
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public Double getPreco() {
		return preco;
	}

	public String getCategoria() {
		return categoria;
	}

	public String getIsTemDesconto() {
		return isTemDesconto;
	}

	public String getIsTemEstoque() {
		return isTemEstoque;
	}

	public String getIsTemFoto() {
		return isTemFoto;
	}

}
