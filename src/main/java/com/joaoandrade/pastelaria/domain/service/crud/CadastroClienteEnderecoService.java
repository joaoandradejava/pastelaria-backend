package com.joaoandrade.pastelaria.domain.service.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaoandrade.pastelaria.domain.exception.EnderecoNaoEncontradoException;
import com.joaoandrade.pastelaria.domain.exception.EntidadeEmUsoException;
import com.joaoandrade.pastelaria.domain.exception.NegocioException;
import com.joaoandrade.pastelaria.domain.model.Cliente;
import com.joaoandrade.pastelaria.domain.model.Endereco;
import com.joaoandrade.pastelaria.domain.repository.EnderecoRepository;

@Service
public class CadastroClienteEnderecoService {

	@Autowired
	private CadastroClienteService cadastroClienteService;

	@Autowired
	private EnderecoRepository repository;

	private Endereco buscarPorId(Long enderecoId) {
		return repository.findById(enderecoId).orElseThrow(() -> new EnderecoNaoEncontradoException(enderecoId));
	}

	public Endereco buscarEnderecoDoCliente(Long clienteId, Long enderecoId) {
		cadastroClienteService.buscarPorId(clienteId);
		buscarPorId(enderecoId);

		return repository.buscarEnderecoDoCliente(clienteId, enderecoId).orElseThrow(() -> new NegocioException(String
				.format("O Endereço de id %d não estar associado com o Cliente de id %d", enderecoId, clienteId)));
	}

	@Transactional
	public Endereco cadastrar(Endereco endereco, Long clienteId) {
		Cliente cliente = cadastroClienteService.buscarPorId(clienteId);
		endereco.setCliente(cliente);

		return repository.save(endereco);
	}

	@Transactional
	public void deletarEnderecoDoCliente(Long clienteId, Long enderecoId) {
		Endereco endereco = buscarEnderecoDoCliente(clienteId, enderecoId);

		try {
			repository.deleteById(endereco.getId());
			repository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Não foi possivel deletar o Endereço pois ele estar em uso no sistema!"));
		}

	}

}
