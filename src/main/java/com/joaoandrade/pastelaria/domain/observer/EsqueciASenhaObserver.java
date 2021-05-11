package com.joaoandrade.pastelaria.domain.observer;

import com.joaoandrade.pastelaria.domain.model.Cliente;

public class EsqueciASenhaObserver {
	private final Cliente cliente;
	private final String novaSenha;

	public EsqueciASenhaObserver(Cliente cliente, String novaSenha) {
		this.cliente = cliente;
		this.novaSenha = novaSenha;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

}
