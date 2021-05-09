package com.joaoandrade.pastelaria.api.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class EstadoInput {

	@Size(max = 255)
	@NotBlank
	private String nome;

	public EstadoInput() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
