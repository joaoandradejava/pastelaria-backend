package com.joaoandrade.pastelaria.api.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.joaoandrade.pastelaria.api.assembler.CategoriaModelAssembler;
import com.joaoandrade.pastelaria.api.disassembler.CategoriaInputDisassembler;
import com.joaoandrade.pastelaria.api.input.CategoriaInput;
import com.joaoandrade.pastelaria.api.model.CategoriaModel;
import com.joaoandrade.pastelaria.domain.model.Categoria;
import com.joaoandrade.pastelaria.domain.service.crud.CadastroCategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Categoria Controller")
@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	private CadastroCategoriaService cadastroCategoriaService;

	@Autowired
	private CategoriaModelAssembler categoriaModelAssembler;

	@Autowired
	private CategoriaInputDisassembler categoriaInputDisassembler;

	@Operation(summary = "Busca todas as categorias do banco de dados", description = "Busca todas as categorias do banco de dados")
	@GetMapping
	public List<CategoriaModel> buscarTodos() {
		List<Categoria> lista = cadastroCategoriaService.buscarTodos();

		return categoriaModelAssembler.toCollectionModel(lista);
	}

	@Operation(summary = "Busca todas as categorias de forma paginada do banco de dados", description = "Busca todas as categorias de forma paginada do banco de dados")
	@GetMapping("/paginacao")
	public Page<CategoriaModel> buscarTodosPorPaginacao(Pageable pageable, String nome) {
		Page<Categoria> page;

		if (StringUtils.hasLength(nome)) {
			page = cadastroCategoriaService.buscarTodosPorPaginacaoENome(nome, pageable);

			return page.map(categoria -> categoriaModelAssembler.toModel(categoria));
		}

		page = cadastroCategoriaService.buscarTodosPorPaginacao(pageable);

		return page.map(categoria -> categoriaModelAssembler.toModel(categoria));
	}

	@Operation(summary = "Busca uma categoria por id no banco de dados", description = "Busca uma categoria por id no banco de dados")
	@GetMapping("/{id}")
	public CategoriaModel buscarPorId(@PathVariable Long id) {
		Categoria categoria = cadastroCategoriaService.buscarPorId(id);

		return categoriaModelAssembler.toModel(categoria);
	}

	@Operation(summary = "Cadastra uma nova categoria no banco de dados - ADMIN", description = "Cadastra uma nova categoria no banco de dados - ADMIN")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public CategoriaModel cadastrar(@Valid @RequestBody CategoriaInput categoriaInput) {
		Categoria categoria = cadastroCategoriaService
				.cadastrar(categoriaInputDisassembler.toDomainModel(categoriaInput));

		return categoriaModelAssembler.toModel(categoria);
	}

	@Operation(summary = "Atualiza a categoria pelo seu id - ADMIN", description = "Atualiza a categoria pelo seu id - ADMIN")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public CategoriaModel atualizar(@Valid @RequestBody CategoriaInput categoriaInput, @PathVariable Long id) {
		Categoria categoria = cadastroCategoriaService.buscarPorId(id);
		categoriaInputDisassembler.copyToDomainModel(categoriaInput, categoria);
		categoria = cadastroCategoriaService.atualizar(categoria);

		return categoriaModelAssembler.toModel(categoria);
	}

	@Operation(summary = "Deleta a categoria pelo seu id - ADMIN", description = "Deleta a categoria pelo seu id - ADMIN")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deletarPorId(@PathVariable Long id) {
		cadastroCategoriaService.deletarPorId(id);
	}
}
