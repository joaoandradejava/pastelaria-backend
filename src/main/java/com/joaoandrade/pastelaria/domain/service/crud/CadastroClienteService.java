package com.joaoandrade.pastelaria.domain.service.crud;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaoandrade.pastelaria.domain.exception.ClienteNaoEncontradoException;
import com.joaoandrade.pastelaria.domain.exception.EntidadeEmUsoException;
import com.joaoandrade.pastelaria.domain.model.Cliente;
import com.joaoandrade.pastelaria.domain.repository.ClienteRepository;
import com.joaoandrade.pastelaria.domain.service.validation.CpfUniqueValidator;
import com.joaoandrade.pastelaria.domain.service.validation.EmailUniqueValidator;

@Service
public class CadastroClienteService {

	@Autowired
	private ClienteRepository repository;

	@Autowired
	private EmailUniqueValidator emailUniqueValidator;

	@Autowired
	private CpfUniqueValidator cpfUniqueValidator;

	@Autowired
	private EntityManager entityManager;

	public List<Cliente> buscarTodos() {
		return repository.findAll();
	}

	public Page<Cliente> buscarTodosPorPaginacao(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Page<Cliente> buscarTodosPorPaginacaoENome(String nome, Pageable pageable) {
		return repository.buscarTodosPorPaginacaoENome(nome, pageable);
	}

	public Cliente buscarPorId(Long id) {
		return repository.findById(id).orElseThrow(() -> new ClienteNaoEncontradoException(id));
	}

	@Transactional
	public Cliente cadastrar(Cliente cliente) {
		emailUniqueValidator.validarSeEmailEValido(cliente);

		return repository.save(cliente);
	}

	@Transactional
	public Cliente atualizar(Cliente cliente) {
		entityManager.detach(cliente);
		cpfUniqueValidator.validarSeCpfEValido(cliente);

		return repository.save(cliente);
	}

	@Transactional
	public void deletarPorId(Long id) {
		Cliente cliente = buscarPorId(id);

		try {
			repository.deleteById(id);
			repository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(
					"Não foi possivel deletar o cliente de '%s' pois ele estar em uso no sistema!", cliente.getNome()));
		}

	}

}
