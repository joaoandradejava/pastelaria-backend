package com.joaoandrade.pastelaria.domain.service;

import org.springframework.stereotype.Service;

import com.joaoandrade.pastelaria.core.security.ClienteAutenticado;

@Service
public class PermissaoService {

	public boolean isNaoTemAutorizacaoDeAdministrador(ClienteAutenticado clienteAutenticado, Long id) {
		return clienteAutenticado.getId() != id && !clienteAutenticado.getIsAdmin();
	}
}
