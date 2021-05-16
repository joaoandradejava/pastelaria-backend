package com.joaoandrade.pastelaria.api.model;

public class ProdutoAdminModel {
	private Long id;
	private String nome;
	private Double preco;
	private Boolean isTemDesconto;
	private Boolean isTemEstoque;
	private Boolean isTemFoto;

	public ProdutoAdminModel() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Boolean getIsTemDesconto() {
		return isTemDesconto;
	}

	public void setIsTemDesconto(Boolean isTemDesconto) {
		this.isTemDesconto = isTemDesconto;
	}

	public Boolean getIsTemEstoque() {
		return isTemEstoque;
	}

	public void setIsTemEstoque(Boolean isTemEstoque) {
		this.isTemEstoque = isTemEstoque;
	}

	public Boolean getIsTemFoto() {
		return isTemFoto;
	}

	public void setIsTemFoto(Boolean isTemFoto) {
		this.isTemFoto = isTemFoto;
	}

}
