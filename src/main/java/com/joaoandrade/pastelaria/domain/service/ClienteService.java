package com.joaoandrade.pastelaria.domain.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaoandrade.pastelaria.domain.exception.NegocioException;
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

	@Transactional
	public void mudarSenha(Long id, String senhaAtual, String novaSenha) {
		Cliente cliente = cadastroClienteService.buscarPorId(id);
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

		if (!bCryptPasswordEncoder.matches(senhaAtual, cliente.getSenha())) {
			throw new NegocioException(String.format("A senha atual informada não corresponde a sua senha"));
		}

		cliente.setSenha(bCryptPasswordEncoder.encode(novaSenha));
	}

	@Transactional
	public String esqueciASenha(String email) {
		Cliente cliente = cadastroClienteService.buscarPorEmail(email);
		String novaSenha = "CarraraTaxi";
		Random random = new Random();
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

		char[] letras = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', '1', '2', '3',
				'4', '5', '6' };

		for (int i = 0; i < letras.length / 2; i++) {
			novaSenha += letras[random.nextInt(letras.length)];
		}

		cliente.setSenha(bCryptPasswordEncoder.encode(novaSenha));

		return novaSenha;
	}

}
