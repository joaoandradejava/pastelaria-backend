package com.joaoandrade.pastelaria.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
import com.joaoandrade.pastelaria.api.input.EsqueciSenhaInput;
import com.joaoandrade.pastelaria.api.input.MudancaSenhaInput;
import com.joaoandrade.pastelaria.api.model.ClienteFullModel;
import com.joaoandrade.pastelaria.api.model.ClienteModel;
import com.joaoandrade.pastelaria.core.security.ClienteAutenticado;
import com.joaoandrade.pastelaria.domain.exception.AcessoNegadoException;
import com.joaoandrade.pastelaria.domain.exception.NegocioException;
import com.joaoandrade.pastelaria.domain.model.Cliente;
import com.joaoandrade.pastelaria.domain.observer.CadastroClienteObserver;
import com.joaoandrade.pastelaria.domain.observer.EsqueciASenhaObserver;
import com.joaoandrade.pastelaria.domain.service.ClienteService;
import com.joaoandrade.pastelaria.domain.service.PermissaoService;
import com.joaoandrade.pastelaria.domain.service.crud.CadastroClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cliente Controller")
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

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Operation(summary = "Busca todos os clientes - ADMIN", description = "Busca todos os clientes - ADMIN")
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public List<ClienteModel> buscarTodos() {
		List<Cliente> lista = cadastroClienteService.buscarTodos();

		return clienteModelAssembler.toCollectionModel(lista);
	}

	@Operation(summary = "Busca todos os clientes por pagina????o - ADMIN", description = "Busca todos os clientes por pagina????o - ADMIN")
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

	@Operation(summary = "Busca os dados do cliente por id", description = "Busca os dados do cliente por id")
	@GetMapping("/{id}")
	public ClienteFullModel buscarPorId(@PathVariable Long id,
			@AuthenticationPrincipal ClienteAutenticado clienteAutenticado) {
		if (permissaoService.isNaoTemAutorizacaoDeAdministrador(clienteAutenticado, id)) {
			throw new AcessoNegadoException(
					"Voc?? n??o tem autoriza????o para acessar os dados de outro cliente no sistema!");
		}

		Cliente cliente = cadastroClienteService.buscarPorId(id);

		return clienteFullModelAssembler.toModel(cliente);
	}

	@Operation(summary = "Cadastra um novo cliente", description = "Cadastra um novo cliente")
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public ClienteModel cadastrar(@Valid @RequestBody ClienteCreateInput clienteCreateInput) {
		if (!clienteCreateInput.getConfirmacaoSenha().equals(clienteCreateInput.getSenha())) {
			throw new NegocioException("As senhas n??o se correspondem");
		}

		Cliente cliente = cadastroClienteService
				.cadastrar(clienteCreateInputDisassembler.toDomainModel(clienteCreateInput));

		applicationEventPublisher.publishEvent(new CadastroClienteObserver(cliente));
		return clienteModelAssembler.toModel(cliente);
	}

	@Operation(summary = "Atualiza o cliente por id", description = "Atualiza o cliente por id")
	@PutMapping("/{id}")
	public ClienteModel atualizar(@Valid @RequestBody ClienteUpdateInput clienteUpdateInput, @PathVariable Long id,
			@AuthenticationPrincipal ClienteAutenticado clienteAutenticado) {
		if (permissaoService.isNaoTemAutorizacaoDeAdministrador(clienteAutenticado, id)) {
			throw new AcessoNegadoException(
					"Voc?? n??o tem autoriza????o para atualizar os dados de outro cliente no sistema!");
		}

		Cliente cliente = cadastroClienteService.buscarPorId(id);
		clienteUpdateInputDisassembler.copyToDomainModel(clienteUpdateInput, cliente);
		cliente = cadastroClienteService.atualizar(cliente);

		return clienteModelAssembler.toModel(cliente);
	}

	@Operation(summary = "Deleta o cliente por id", description = "Deleta o cliente por id")
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deletarPorId(@PathVariable Long id, @AuthenticationPrincipal ClienteAutenticado clienteAutenticado) {
		if (permissaoService.isNaoTemAutorizacaoDeAdministrador(clienteAutenticado, id)) {
			throw new AcessoNegadoException(
					"Voc?? n??o tem autoriza????o para deletar a conta de outro cliente no sistema!");
		}

		cadastroClienteService.deletarPorId(id);
	}

	@Operation(summary = "Ativa a conta do cliente por id", description = "Ativa a conta do cliente por id")
	@PutMapping("/{id}/conta-ativa")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void ativarConta(@PathVariable Long id, @AuthenticationPrincipal ClienteAutenticado clienteAutenticado) {
		if (permissaoService.isNaoTemAutorizacaoDeAdministrador(clienteAutenticado, id)) {
			throw new AcessoNegadoException(
					"Voc?? n??o tem autoriza????o para ativar a conta de outro cliente no sistema!");
		}

		clienteService.ativarConta(id);
	}

	@Operation(summary = "desativa a conta do cliente por id", description = "desativa a conta do cliente por id")
	@DeleteMapping("/{id}/conta-ativa")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void desativarConta(@PathVariable Long id, @AuthenticationPrincipal ClienteAutenticado clienteAutenticado) {
		if (permissaoService.isNaoTemAutorizacaoDeAdministrador(clienteAutenticado, id)) {
			throw new AcessoNegadoException(
					"Voc?? n??o tem autoriza????o para desativar a conta de outro cliente no sistema!");
		}

		clienteService.desativarConta(id);
	}

	@Operation(summary = "Dar fun????o de admin a um cliente", description = "Dar fun????o de admin a um cliente")
	@PutMapping("/{id}/admin")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void darFuncaoDeAdmin(@PathVariable Long id,
			@AuthenticationPrincipal ClienteAutenticado clienteAutenticado) {
		if (permissaoService.isNaoTemAutorizacaoDeAdministrador(clienteAutenticado, id)) {
			throw new AcessoNegadoException(
					"Voc?? n??o tem autoriza????o de dar fun????o de administrador para outro cliente no sistema!");
		}

		clienteService.darFuncaoDeAdmin(id);
	}

	@Operation(summary = "tira a fun????o de admin de um cliente", description = "tira a fun????o de admin de um cliente")
	@DeleteMapping("/{id}/admin")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void tirarFuncaoDeAdmin(@PathVariable Long id,
			@AuthenticationPrincipal ClienteAutenticado clienteAutenticado) {
		if (permissaoService.isNaoTemAutorizacaoDeAdministrador(clienteAutenticado, id)) {
			throw new AcessoNegadoException(
					"Voc?? n??o tem autoriza????o para tirar a fun????o de adminsitrador de um cliente no sistema!");
		}

		clienteService.tirarFuncaoDeAdmin(id);
	}

	@Operation(summary = "Muda a senha do cliente", description = "Muda a senha do cliente")
	@PutMapping("/senha")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void mudarSenha(@Valid @RequestBody MudancaSenhaInput mudancaSenhaInput,
			@AuthenticationPrincipal ClienteAutenticado clienteAutenticado) {
		clienteService.mudarSenha(clienteAutenticado.getId(), mudancaSenhaInput.getSenhaAtual(),
				mudancaSenhaInput.getNovaSenha());
	}

	@Operation(summary = "Envia uma nova senha por email do cliente", description = "Envia uma nova senha por email do cliente")
	@PutMapping("/esqueci-senha")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void esqueciASenha(@Valid @RequestBody EsqueciSenhaInput esqueciSenhaInput) {
		Cliente cliente = cadastroClienteService.buscarPorEmail(esqueciSenhaInput.getEmail());
		String novaSenha = clienteService.esqueciASenha(cliente);

		applicationEventPublisher.publishEvent(new EsqueciASenhaObserver(cliente, novaSenha));
	}
}
