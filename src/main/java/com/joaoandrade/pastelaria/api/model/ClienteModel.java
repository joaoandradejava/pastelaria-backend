package com.joaoandrade.pastelaria.api.model;

public class ClienteModel {
	private Long id;
	private String nome;
	private String email;
	private String telefone;
	private Boolean isContaAtiva;
	private Boolean isAdmin;

	public ClienteModel() {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Boolean getIsContaAtiva() {
		return isContaAtiva;
	}

	public void setIsContaAtiva(Boolean isContaAtiva) {
		this.isContaAtiva = isContaAtiva;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

}
