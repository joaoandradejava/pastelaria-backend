package com.joaoandrade.pastelaria.api.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class ProdutoInput {

	@Size(max = 255)
	@NotBlank
	private String nome;

	@Positive
	@NotNull
	private Double preco;

	@NotBlank
	private String descricao;

	@Valid
	@NotNull
	private CategoriaIdInput categoria;

	public ProdutoInput() {
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

	public CategoriaIdInput getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaIdInput categoria) {
		this.categoria = categoria;
	}

}
