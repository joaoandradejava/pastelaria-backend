package com.joaoandrade.pastelaria.domain.observer;

import com.joaoandrade.pastelaria.domain.model.Cliente;

public class CadastroClienteObserver {
	private final Cliente cliente;

	public CadastroClienteObserver(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cliente getCliente() {
		return cliente;
	}

}
