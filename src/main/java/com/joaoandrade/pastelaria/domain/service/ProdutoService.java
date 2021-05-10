package com.joaoandrade.pastelaria.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaoandrade.pastelaria.domain.model.Produto;
import com.joaoandrade.pastelaria.domain.service.crud.CadastroProdutoService;

@Service
public class ProdutoService {

	@Autowired
	private CadastroProdutoService cadastroProdutoService;

	@Transactional
	public void adicionarDesconto(Integer desconto, Long id) {
		Produto produto = cadastroProdutoService.buscarPorId(id);

		produto.adicionarDesconto(desconto);
	}

	@Transactional
	public void removerDesconto(Long id) {
		Produto produto = cadastroProdutoService.buscarPorId(id);

		produto.removerDesconto();
	}

	@Transactional
	public void colocarProdutoDisponivelNoEstoque(Long id) {
		Produto produto = cadastroProdutoService.buscarPorId(id);

		produto.setIsTemEstoque(true);
	}

	@Transactional
	public void tirarProdutoDisponivelNoEstoque(Long id) {
		Produto produto = cadastroProdutoService.buscarPorId(id);

		produto.setIsTemEstoque(false);
	}

	@Transactional
	public void adicionarFoto(String fotoEmBase64, Long id) {
		Produto produto = cadastroProdutoService.buscarPorId(id);

		produto.adicionarFoto(fotoEmBase64);
	}

	@Transactional
	public void removerFoto(Long id) {
		Produto produto = cadastroProdutoService.buscarPorId(id);

		produto.removerFoto();
	}

}
