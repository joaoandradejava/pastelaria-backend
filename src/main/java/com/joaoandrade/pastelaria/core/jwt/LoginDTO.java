package com.joaoandrade.pastelaria.core.jwt;

public class LoginDTO {
	private String email;
	private String senha;

	public LoginDTO() {
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

}
