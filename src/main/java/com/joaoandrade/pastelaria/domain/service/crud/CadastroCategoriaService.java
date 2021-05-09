package com.joaoandrade.pastelaria.domain.service.crud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaoandrade.pastelaria.domain.exception.EntidadeEmUsoException;
import com.joaoandrade.pastelaria.domain.exception.CategoriaNaoEncontradaException;
import com.joaoandrade.pastelaria.domain.model.Categoria;
import com.joaoandrade.pastelaria.domain.repository.CategoriaRepository;

@Service
public class CadastroCategoriaService {

	@Autowired
	private CategoriaRepository repository;

	public List<Categoria> buscarTodos() {
		return repository.findAll();
	}

	public Page<Categoria> buscarTodosPorPaginacao(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Page<Categoria> buscarTodosPorPaginacaoENome(String nome, Pageable pageable) {
		return repository.buscarTodosPorPaginacaoENome(nome, pageable);
	}

	public Categoria buscarPorId(Long id) {
		return repository.findById(id).orElseThrow(() -> new CategoriaNaoEncontradaException(id));
	}

	@Transactional
	public Categoria cadastrar(Categoria categoria) {
		return repository.save(categoria);
	}

	@Transactional
	public Categoria atualizar(Categoria categoria) {
		return repository.save(categoria);
	}

	@Transactional
	public void deletarPorId(Long id) {
		Categoria categoria = buscarPorId(id);

		if (categoria.getProdutos().size() > 0) {
			throw new EntidadeEmUsoException(String.format(
					"Não foi possivel deletar a categoria de '%s' pois ela tem produtos associados a ela!",
					categoria.getNome()));
		}

		try {
			repository.deleteById(id);
			repository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Não foi possivel deletar a categoria de '%s' pois ela estar em uso no sistema!",
							categoria.getNome()));
		}

	}

}
