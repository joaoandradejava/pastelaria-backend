package com.joaoandrade.pastelaria.api.input;

import javax.validation.constraints.NotNull;

public class EnderecoIdInput {

	@NotNull
	private Long id;

	public EnderecoIdInput() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
