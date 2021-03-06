package com.joaoandrade.pastelaria.api.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CategoriaInput {

	@Size(max = 255)
	@NotBlank
	private String nome;

	public CategoriaInput() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
