package com.joaoandrade.pastelaria.api.input;

import javax.validation.constraints.NotNull;

public class EstadoIdInput {

	@NotNull
	private Long id;

	public EstadoIdInput() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
