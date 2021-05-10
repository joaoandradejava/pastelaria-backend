package com.joaoandrade.pastelaria.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

import com.joaoandrade.pastelaria.api.assembler.ClienteFullModelAssembler;
import com.joaoandrade.pastelaria.api.assembler.ClienteModelAssembler;
import com.joaoandrade.pastelaria.api.disassembler.ClienteCreateInputDisassembler;
import com.joaoandrade.pastelaria.api.disassembler.ClienteUpdateInputDisassembler;
import com.joaoandrade.pastelaria.api.input.ClienteCreateInput;
import com.joaoandrade.pastelaria.api.input.ClienteUpdateInput;
import com.joaoandrade.pastelaria.api.model.ClienteFullModel;
import com.joaoandrade.pastelaria.api.model.ClienteModel;
import com.joaoandrade.pastelaria.core.security.ClienteAutenticado;
import com.joaoandrade.pastelaria.domain.exception.AcessoNegadoException;
import com.joaoandrade.pastelaria.domain.exception.NegocioException;
import com.joaoandrade.pastelaria.domain.model.Cliente;
import com.joaoandrade.pastelaria.domain.service.ClienteService;
import com.joaoandrade.pastelaria.domain.service.PermissaoService;
import com.joaoandrade.pastelaria.domain.service.crud.CadastroClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private CadastroClienteService cadastroClienteService;

	@Autowired
	private ClienteModelAssembler clienteModelAssembler;

	@Autowired
	private ClienteCreateInputDisassembler clienteCreateInputDisassembler;

	@Autowired
	private ClienteUpdateInputDisassembler clienteUpdateInputDisassembler;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ClienteFullModelAssembler clienteFullModelAssembler;

	@Autowired
	private PermissaoService permissaoService;

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public List<ClienteModel> buscarTodos() {
		List<Cliente> lista = cadastroClienteService.buscarTodos();

		return clienteModelAssembler.toCollectionModel(lista);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/paginacao")
	public Page<ClienteModel> buscarTodosPorPaginacao(Pageable pageable, String nome) {
		Page<Cliente> page;

		if (StringUtils.hasLength(nome)) {
			page = cadastroClienteService.buscarTodosPorPaginacaoENome(nome, pageable);

			return page.map(cliente -> clienteModelAssembler.toModel(cliente));
		}

		page = cadastroClienteService.buscarTodosPorPaginacao(pageable);

		return page.map(cliente -> clienteModelAssembler.toModel(cliente));
	}

	@GetMapping("/{id}")
	public ClienteFullModel buscarPorId(@PathVariable Long id,
			@AuthenticationPrincipal ClienteAutenticado clienteAutenticado) {
		if (permissaoService.isNaoTemAutorizacaoDeAdministrador(clienteAutenticado, id)) {
			throw new AcessoNegadoException(
					"Você não tem autorização para acessar os dados de outro cliente no sistema!");
		}

		Cliente cliente = cadastroClienteService.buscarPorId(id);

		return clienteFullModelAssembler.toModel(cliente);
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public ClienteModel cadastrar(@Valid @RequestBody ClienteCreateInput clienteCreateInput) {
		if (!clienteCreateInput.getConfirmacaoSenha().equals(clienteCreateInput.getSenha())) {
			throw new NegocioException("As senhas não se correspondem");
		}

		Cliente cliente = cadastroClienteService
				.cadastrar(clienteCreateInputDisassembler.toDomainModel(clienteCreateInput));

		return clienteModelAssembler.toModel(cliente);
	}

	@PutMapping("/{id}")
	public ClienteModel atualizar(@Valid @RequestBody ClienteUpdateInput clienteUpdateInput, @PathVariable Long id,
			@AuthenticationPrincipal ClienteAutenticado clienteAutenticado) {
		if (permissaoService.isNaoTemAutorizacaoDeAdministrador(clienteAutenticado, id)) {
			throw new AcessoNegadoException(
					"Você não tem autorização para atualizar os dados de outro cliente no sistema!");
		}

		Cliente cliente = cadastroClienteService.buscarPorId(id);
		clienteUpdateInputDisassembler.copyToDomainModel(clienteUpdateInput, cliente);
		cliente = cadastroClienteService.atualizar(cliente);

		return clienteModelAssembler.toModel(cliente);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deletarPorId(@PathVariable Long id, @AuthenticationPrincipal ClienteAutenticado clienteAutenticado) {
		if (permissaoService.isNaoTemAutorizacaoDeAdministrador(clienteAutenticado, id)) {
			throw new AcessoNegadoException(
					"Você não tem autorização para deletar a conta de outro cliente no sistema!");
		}

		cadastroClienteService.deletarPorId(id);
	}

	@PutMapping("/{id}/conta-ativa")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void ativarConta(@PathVariable Long id, @AuthenticationPrincipal ClienteAutenticado clienteAutenticado) {
		if (permissaoService.isNaoTemAutorizacaoDeAdministrador(clienteAutenticado, id)) {
			throw new AcessoNegadoException(
					"Você não tem autorização para ativar a conta de outro cliente no sistema!");
		}

		clienteService.ativarConta(id);
	}

	@DeleteMapping("/{id}/conta-ativa")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void desativarConta(@PathVariable Long id, @AuthenticationPrincipal ClienteAutenticado clienteAutenticado) {
		if (permissaoService.isNaoTemAutorizacaoDeAdministrador(clienteAutenticado, id)) {
			throw new AcessoNegadoException(
					"Você não tem autorização para desativar a conta de outro cliente no sistema!");
		}

		clienteService.desativarConta(id);
	}

	@PutMapping("/{id}/admin")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void darFuncaoDeAdmin(@PathVariable Long id,
			@AuthenticationPrincipal ClienteAutenticado clienteAutenticado) {
		if (permissaoService.isNaoTemAutorizacaoDeAdministrador(clienteAutenticado, id)) {
			throw new AcessoNegadoException(
					"Você não tem autorização de dar função de administrador para outro cliente no sistema!");
		}

		clienteService.darFuncaoDeAdmin(id);
	}

	@DeleteMapping("/{id}/admin")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void tirarFuncaoDeAdmin(@PathVariable Long id,
			@AuthenticationPrincipal ClienteAutenticado clienteAutenticado) {
		if (permissaoService.isNaoTemAutorizacaoDeAdministrador(clienteAutenticado, id)) {
			throw new AcessoNegadoException(
					"Você não tem autorização para tirar a função de adminsitrador de um cliente no sistema!");
		}

		clienteService.tirarFuncaoDeAdmin(id);
	}
}
