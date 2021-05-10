package com.joaoandrade.pastelaria.api.input;

import javax.validation.constraints.NotNull;

import com.joaoandrade.pastelaria.domain.service.validation.annotation.Desconto;

public class ProdutoDescontoInput {

	@NotNull
	@Desconto
	private Integer desconto;

	public ProdutoDescontoInput() {
	}

	public Integer getDesconto() {
		return desconto;
	}

	public void setDesconto(Integer desconto) {
		this.desconto = desconto;
	}

}
