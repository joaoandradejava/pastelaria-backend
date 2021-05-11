package com.joaoandrade.pastelaria.api.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.joaoandrade.pastelaria.api.assembler.ProdutoFullModelAssembler;
import com.joaoandrade.pastelaria.api.assembler.ProdutoModelAssembler;
import com.joaoandrade.pastelaria.api.disassembler.ProdutoInputDisassembler;
import com.joaoandrade.pastelaria.api.input.ProdutoDescontoInput;
import com.joaoandrade.pastelaria.api.input.ProdutoInput;
import com.joaoandrade.pastelaria.api.model.ProdutoFullModel;
import com.joaoandrade.pastelaria.api.model.ProdutoModel;
import com.joaoandrade.pastelaria.domain.exception.CategoriaNaoEncontradaException;
import com.joaoandrade.pastelaria.domain.exception.NegocioException;
import com.joaoandrade.pastelaria.domain.model.Produto;
import com.joaoandrade.pastelaria.domain.service.ProdutoService;
import com.joaoandrade.pastelaria.domain.service.crud.CadastroProdutoService;
import com.joaoandrade.pastelaria.domain.util.FormatoImagem;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Produto Controller")
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private CadastroProdutoService cadastroProdutoService;

	@Autowired
	private ProdutoModelAssembler produtoModelAssembler;

	@Autowired
	private ProdutoInputDisassembler produtoInputDisassembler;

	@Autowired
	private ProdutoFullModelAssembler produtoFullModelAssembler;

	@Autowired
	private ProdutoService produtoService;

	@Operation(summary = "Busca todos os produtos do sistema - ADMIN", description = "Busca todos os produtos do sistema - ADMIN")
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public List<ProdutoModel> buscarTodos() {
		List<Produto> lista = cadastroProdutoService.buscarTodos();

		return produtoModelAssembler.toCollectionModel(lista);
	}

	@Operation(summary = "Busca todos os produtos do sistema por paginação - ADMIN", description = "Busca todos os produtos do sistema por paginação - ADMIN")
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/paginacao")
	public Page<ProdutoModel> buscarTodosPorPaginacao(Pageable pageable, String nome) {
		Page<Produto> page;

		if (StringUtils.hasLength(nome)) {
			page = cadastroProdutoService.buscarTodosPorPaginacaoENome(nome, pageable);

			return page.map(produto -> produtoModelAssembler.toModel(produto));
		}

		page = cadastroProdutoService.buscarTodosPorPaginacao(pageable);

		return page.map(produto -> produtoModelAssembler.toModel(produto));
	}

	@Operation(summary = "Busca todos os produtos do sistema por paginação disponiveis no estoque", description = "Busca todos os produtos do sistema por paginação disponiveis no estoque")
	@GetMapping("/disponivel-estoque/paginacao")
	public Page<ProdutoModel> buscarTodosProdutosDisponiveisNoEstoque(Pageable pageable, String nome) {
		Page<Produto> page;

		if (StringUtils.hasLength(nome)) {
			page = cadastroProdutoService.buscarTodosProdutosDisponiveisNoEstoqueEPorNome(pageable, nome);

			return page.map(produto -> produtoModelAssembler.toModel(produto));
		}

		page = cadastroProdutoService.buscarTodosProdutosDisponiveisNoEstoque(pageable);
		return page.map(produto -> produtoModelAssembler.toModel(produto));
	}

	@Operation(summary = "Busca um produto pelo seu id", description = "Busca um produto pelo seu id")
	@GetMapping("/{id}")
	public ProdutoFullModel buscarPorId(@PathVariable Long id) {
		Produto produto = cadastroProdutoService.buscarPorId(id);

		return produtoFullModelAssembler.toModel(produto);
	}

	@Operation(summary = "Cadastra um novo produto no banco de dados - ADMIN", description = "Cadastra um novo produto no banco de dados - ADMIN")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public ProdutoFullModel cadastrar(@Valid @RequestBody ProdutoInput produtoInput) {
		try {
			Produto produto = cadastroProdutoService.cadastrar(produtoInputDisassembler.toDomainModel(produtoInput));

			return produtoFullModelAssembler.toModel(produto);
		} catch (CategoriaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@Operation(summary = "Atualiza um produto pelo seu id - ADMIN", description = "Atualiza um produto pelo seu id - ADMIN")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ProdutoFullModel atualizar(@Valid @RequestBody ProdutoInput produtoInput, @PathVariable Long id) {
		try {
			Produto produto = cadastroProdutoService.buscarPorId(id);
			produtoInputDisassembler.copyToDomainModel(produtoInput, produto);
			produto = cadastroProdutoService.atualizar(produto);

			return produtoFullModelAssembler.toModel(produto);
		} catch (CategoriaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@Operation(summary = "deleta um produto pelo seu id - ADMIN", description = "deleta um produto pelo seu id - ADMIN")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deletarPorId(@PathVariable Long id) {
		cadastroProdutoService.deletarPorId(id);
	}

	@Operation(summary = "Adiciona desconto no preço do produto - ADMIN", description = "Adiciona desconto no preço do produto - ADMIN")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/desconto")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void adicionarDesconto(@Valid @RequestBody ProdutoDescontoInput produtoDescontoInput,
			@PathVariable Long id) {
		produtoService.adicionarDesconto(produtoDescontoInput.getDesconto(), id);
	}

	@Operation(summary = "remove o desconto no preço do produto - ADMIN", description = " remove o desconto no preço do produto - ADMIN")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}/desconto")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void removerDesconto(@PathVariable Long id) {
		produtoService.removerDesconto(id);
	}

	@Operation(summary = "Coloca produto disponivel no estoque - ADMIN", description = "Coloca produto disponivel no estoque - ADMIN")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/estoque")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void colocarProdutoDisponivelNoEstoque(@PathVariable Long id) {
		produtoService.colocarProdutoDisponivelNoEstoque(id);
	}

	@Operation(summary = "Tira produto do estoque - ADMIN", description = "Tira produto do estoque - ADMIN")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}/estoque")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void tirarProdutoDisponivelNoEstoque(@PathVariable Long id) {
		produtoService.tirarProdutoDisponivelNoEstoque(id);
	}

	@Operation(summary = "Adiciona foto no produto - ADMIN", description = "Adiciona foto no produto - ADMIN")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/foto")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void adicionarFoto(MultipartFile foto, @PathVariable Long id) {
		if (FormatoImagem.isFormatoImagem(foto.getContentType())) {
			try {
				String fotoEmBase64 = "data:image/png;base64," + Base64.encodeBase64String(foto.getBytes());

				produtoService.adicionarFoto(fotoEmBase64, id);
			} catch (IOException e) {
				throw new NegocioException(
						"Ocorreu um erro inesperado ao tentar transformar a imagem do produto em base 64");
			}
		}

	}

	@Operation(summary = "Remove foto do produto - ADMIN", description = "Remove foto do produto - ADMIN")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}/foto")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void removerFoto(@PathVariable Long id) {
		produtoService.removerFoto(id);
	}

}
