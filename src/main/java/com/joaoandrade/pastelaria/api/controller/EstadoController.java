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

import com.joaoandrade.pastelaria.api.assembler.EstadoModelAssembler;
import com.joaoandrade.pastelaria.api.disassembler.EstadoInputDisassembler;
import com.joaoandrade.pastelaria.api.input.EstadoInput;
import com.joaoandrade.pastelaria.api.model.EstadoModel;
import com.joaoandrade.pastelaria.domain.model.Estado;
import com.joaoandrade.pastelaria.domain.service.crud.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private CadastroEstadoService cadastroEstadoService;

	@Autowired
	private EstadoModelAssembler estadoModelAssembler;

	@Autowired
	private EstadoInputDisassembler estadoInputDisassembler;

	@GetMapping
	public List<EstadoModel> buscarTodos() {
		List<Estado> lista = cadastroEstadoService.buscarTodos();

		return estadoModelAssembler.toCollectionModel(lista);
	}

	@GetMapping("/paginacao")
	public Page<EstadoModel> buscarTodosPorPaginacao(Pageable pageable, String nome) {
		Page<Estado> page;

		if (StringUtils.hasLength(nome)) {
			page = cadastroEstadoService.buscarTodosPorPaginacaoENome(nome, pageable);

			return page.map(estado -> estadoModelAssembler.toModel(estado));
		}

		page = cadastroEstadoService.buscarTodosPorPaginacao(pageable);

		return page.map(estado -> estadoModelAssembler.toModel(estado));
	}

	@GetMapping("/{id}")
	public EstadoModel buscarPorId(@PathVariable Long id) {
		Estado estado = cadastroEstadoService.buscarPorId(id);

		return estadoModelAssembler.toModel(estado);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public EstadoModel cadastrar(@Valid @RequestBody EstadoInput estadoInput) {
		Estado estado = cadastroEstadoService.cadastrar(estadoInputDisassembler.toDomainModel(estadoInput));

		return estadoModelAssembler.toModel(estado);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public EstadoModel atualizar(@Valid @RequestBody EstadoInput estadoInput, @PathVariable Long id) {
		Estado estado = cadastroEstadoService.buscarPorId(id);
		estadoInputDisassembler.copyToDomainModel(estadoInput, estado);
		estado = cadastroEstadoService.atualizar(estado);

		return estadoModelAssembler.toModel(estado);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deletarPorId(@PathVariable Long id) {
		cadastroEstadoService.deletarPorId(id);
	}
}
