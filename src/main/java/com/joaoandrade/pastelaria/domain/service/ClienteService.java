package com.joaoandrade.pastelaria.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaoandrade.pastelaria.domain.model.Cliente;
import com.joaoandrade.pastelaria.domain.model.enumeration.Funcao;
import com.joaoandrade.pastelaria.domain.service.crud.CadastroClienteService;

@Service
public class ClienteService {

	@Autowired
	private CadastroClienteService cadastroClienteService;

	@Transactional
	public void ativarConta(Long id) {
		Cliente cliente = cadastroClienteService.buscarPorId(id);

		cliente.ativarConta();
	}

	@Transactional
	public void desativarConta(Long id) {
		Cliente cliente = cadastroClienteService.buscarPorId(id);

		cliente.desativarConta();
	}

	@Transactional
	public void darFuncaoDeAdmin(Long id) {
		Cliente cliente = cadastroClienteService.buscarPorId(id);

		cliente.adicionarFuncao(Funcao.ADMIN);
	}

	@Transactional
	public void tirarFuncaoDeAdmin(Long id) {
		Cliente cliente = cadastroClienteService.buscarPorId(id);

		cliente.removerFuncao(Funcao.ADMIN);
	}

}
