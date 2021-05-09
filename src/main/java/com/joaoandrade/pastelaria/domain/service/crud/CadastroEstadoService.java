package com.joaoandrade.pastelaria.domain.service.crud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaoandrade.pastelaria.domain.exception.EntidadeEmUsoException;
import com.joaoandrade.pastelaria.domain.exception.EstadoNaoEncontradoException;
import com.joaoandrade.pastelaria.domain.model.Estado;
import com.joaoandrade.pastelaria.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	@Autowired
	private EstadoRepository repository;

	public List<Estado> buscarTodos() {
		return repository.findAll();
	}

	public Page<Estado> buscarTodosPorPaginacao(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Page<Estado> buscarTodosPorPaginacaoENome(String nome, Pageable pageable) {
		return repository.buscarTodosPorPaginacaoENome(nome, pageable);
	}

	public Estado buscarPorId(Long id) {
		return repository.findById(id).orElseThrow(() -> new EstadoNaoEncontradoException(id));
	}

	@Transactional
	public Estado cadastrar(Estado estado) {
		return repository.save(estado);
	}

	@Transactional
	public Estado atualizar(Estado estado) {
		return repository.save(estado);
	}

	@Transactional
	public void deletarPorId(Long id) {
		Estado estado = buscarPorId(id);

		try {
			repository.deleteById(id);
			repository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(
					"Não foi possivel deletar o estado de '%s' pois ele estar em uso no sistema!", estado.getNome()));
		}

	}

}
