package com.joaoandrade.pastelaria.domain.service.crud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaoandrade.pastelaria.domain.exception.EntidadeEmUsoException;
import com.joaoandrade.pastelaria.domain.exception.ProdutoNaoEncontradoException;
import com.joaoandrade.pastelaria.domain.model.Produto;
import com.joaoandrade.pastelaria.domain.repository.ProdutoRepository;

@Service
public class CadastroProdutoService {

	@Autowired
	private ProdutoRepository repository;

	@Autowired
	private CadastroCategoriaService cadastroCategoriaService;

	public List<Produto> buscarTodos() {
		return repository.findAll();
	}

	public Page<Produto> buscarTodosPorPaginacao(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Page<Produto> buscarTodosPorPaginacaoENome(String nome, Pageable pageable) {
		return repository.buscarTodosPorPaginacaoENome(nome, pageable);
	}

	public Page<Produto> buscarTodosProdutosDisponiveisNoEstoque(Pageable pageable) {
		return repository.buscarTodosProdutosDisponiveisNoEstoque(pageable);
	}

	public Page<Produto> buscarTodosProdutosDisponiveisNoEstoqueEPorNome(Pageable pageable, String nome) {
		return repository.buscarTodosProdutosDisponiveisNoEstoqueEPorNome(nome, pageable);
	}

	public Produto buscarPorId(Long id) {
		return repository.findById(id).orElseThrow(() -> new ProdutoNaoEncontradoException(id));
	}

	@Transactional
	public Produto cadastrar(Produto produto) {
		produto.setCategoria(cadastroCategoriaService.buscarPorId(produto.getCategoria().getId()));

		return repository.save(produto);
	}

	@Transactional
	public Produto atualizar(Produto produto) {
		produto.setCategoria(cadastroCategoriaService.buscarPorId(produto.getCategoria().getId()));

		return repository.save(produto);
	}

	@Transactional
	public void deletarPorId(Long id) {
		Produto produto = buscarPorId(id);

		try {
			repository.deleteById(id);
			repository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(
					"Não foi possivel deletar o produto de '%s' pois ele estar em uso no sistema!", produto.getNome()));
		}

	}

}
