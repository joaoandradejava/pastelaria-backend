package com.joaoandrade.pastelaria.api.input;

import javax.validation.constraints.NotNull;

public class CategoriaIdInput {

	@NotNull
	private Long id;

	public CategoriaIdInput() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
