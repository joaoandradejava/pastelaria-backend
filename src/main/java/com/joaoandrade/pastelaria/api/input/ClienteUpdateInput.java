package com.joaoandrade.pastelaria.api.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

public class ClienteUpdateInput {

	@Size(min = 3, max = 255)
	@NotBlank
	private String nome;

	@Size(max = 11)
	@CPF
	private String cpf;

	@NotBlank
	@Size(max = 11)
	private String telefone;

	@Size(max = 11)
	private String celular;

	public ClienteUpdateInput() {
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

}
