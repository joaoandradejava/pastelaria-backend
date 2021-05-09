package com.joaoandrade.pastelaria.api.model;

import java.util.ArrayList;
import java.util.List;

public class ClienteFullModel {
	private Long id;
	private String nome;
	private String cpf;
	private String email;
	private String telefone;
	private String celular;
	private Boolean isContaAtiva;
	private Boolean isAdmin;
	private List<EnderecoModel> enderecos = new ArrayList<>();

	public ClienteFullModel() {
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
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

	public List<EnderecoModel> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<EnderecoModel> enderecos) {
		this.enderecos = enderecos;
	}

}
