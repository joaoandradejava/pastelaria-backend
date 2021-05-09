package com.joaoandrade.pastelaria.api.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ClienteCreateInput {

	@Size(min = 3, max = 255)
	@NotBlank
	private String nome;

	@Size(max = 255)
	@Email
	@NotBlank
	private String email;

	@Size(max = 255)
	@NotBlank
	private String senha;

	@Size(max = 255)
	@NotBlank
	private String confirmacaoSenha;

	@Size(max = 11)
	@NotBlank
	private String telefone;

	public ClienteCreateInput() {
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

}
