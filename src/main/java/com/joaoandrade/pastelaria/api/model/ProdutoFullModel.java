package com.joaoandrade.pastelaria.api.model;

public class ProdutoFullModel {
	private Long id;
	private String nome;
	private Double preco;
	private String descricao;
	private String avatarUrl;
	private Integer desconto;
	private Boolean isTemDesconto;
	private Boolean isTemEstoque;
	private CategoriaModel categoria;

	public ProdutoFullModel() {
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public Integer getDesconto() {
		return desconto;
	}

	public void setDesconto(Integer desconto) {
		this.desconto = desconto;
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

	public CategoriaModel getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaModel categoria) {
		this.categoria = categoria;
	}

}
