package com.joaoandrade.pastelaria.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private Double preco;
	private String descricao;
	private String avatarUrl;
	private Integer desconto;
	private Boolean isTemDesconto = Boolean.FALSE;
	private Boolean isTemEstoque = Boolean.FALSE;

	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;

	public Produto() {
	}

	public Produto(Long id, String nome, Double preco, String descricao, String avatarUrl, Integer desconto,
			Boolean isTemDesconto, Boolean isTemEstoque, Categoria categoria) {
		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.descricao = descricao;
		this.avatarUrl = avatarUrl;
		this.desconto = desconto;
		this.isTemDesconto = isTemDesconto;
		this.isTemEstoque = isTemEstoque;
		this.categoria = categoria;
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
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
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
