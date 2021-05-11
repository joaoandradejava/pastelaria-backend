package com.joaoandrade.pastelaria.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.joaoandrade.pastelaria.api.assembler.EnderecoModelAssembler;
import com.joaoandrade.pastelaria.api.disassembler.EnderecoInputDisassembler;
import com.joaoandrade.pastelaria.api.input.EnderecoInput;
import com.joaoandrade.pastelaria.api.model.EnderecoModel;
import com.joaoandrade.pastelaria.core.security.ClienteAutenticado;
import com.joaoandrade.pastelaria.domain.exception.AcessoNegadoException;
import com.joaoandrade.pastelaria.domain.exception.EstadoNaoEncontradoException;
import com.joaoandrade.pastelaria.domain.exception.NegocioException;
import com.joaoandrade.pastelaria.domain.model.Cliente;
import com.joaoandrade.pastelaria.domain.model.Endereco;
import com.joaoandrade.pastelaria.domain.service.PermissaoService;
import com.joaoandrade.pastelaria.domain.service.crud.CadastroClienteEnderecoService;
import com.joaoandrade.pastelaria.domain.service.crud.CadastroClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cliente Endereço Controller")
@RestController
@RequestMapping("/clientes/{clienteId}/enderecos")
public class ClienteEnderecoController {

	@Autowired
	private CadastroClienteService cadastroClienteService;

	@Autowired
	private EnderecoModelAssembler enderecoModelAssembler;

	@Autowired
	private CadastroClienteEnderecoService cadastroClienteEnderecoService;

	@Autowired
	private EnderecoInputDisassembler enderecoInputDisassembler;

	@Autowired
	private PermissaoService permissaoService;

	@Operation(summary = "Busca todos os endereços do cliente", description = "Busca todos os endereços do cliente")
	@GetMapping
	public List<EnderecoModel> buscarTodos(@PathVariable Long clienteId,
			@AuthenticationPrincipal ClienteAutenticado clienteAutenticado) {
		if (permissaoService.isNaoTemAutorizacaoDeAdministrador(clienteAutenticado, clienteId)) {
			throw new AcessoNegadoException(
					"Você não tem autorização para acessar as informações dos endereços de outro cliente no sistema!");
		}

		Cliente cliente = cadastroClienteService.buscarPorId(clienteId);

		return enderecoModelAssembler.toCollectionModel(cliente.getEnderecos());
	}

	@Operation(summary = "Busca Endereço do cliente por id", description = "Busca Endereço do cliente por id")
	@GetMapping("/{enderecoId}")
	public EnderecoModel buscarEnderecoDoCliente(@PathVariable Long clienteId, @PathVariable Long enderecoId,
			@AuthenticationPrincipal ClienteAutenticado clienteAutenticado) {
		if (permissaoService.isNaoTemAutorizacaoDeAdministrador(clienteAutenticado, clienteId)) {
			throw new AcessoNegadoException(
					"Você não tem autorização para acessar a informação do endereço de outro cliente no sistema!");
		}

		Endereco endereco = cadastroClienteEnderecoService.buscarEnderecoDoCliente(clienteId, enderecoId);

		return enderecoModelAssembler.toModel(endereco);
	}

	@Operation(summary = "Cadastra um novo endereço", description = "Cadastra um novo endereço")
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public EnderecoModel cadastrar(@Valid @RequestBody EnderecoInput enderecoInput, @PathVariable Long clienteId,
			@AuthenticationPrincipal ClienteAutenticado clienteAutenticado) {
		if (permissaoService.isNaoTemAutorizacaoDeAdministrador(clienteAutenticado, clienteId)) {
			throw new AcessoNegadoException(
					"Você não tem autorização para cadastrar o endereço de um outro cliente no sistema!");
		}

		try {
			Endereco endereco = cadastroClienteEnderecoService
					.cadastrar(enderecoInputDisassembler.toDomainModel(enderecoInput), clienteId);

			return enderecoModelAssembler.toModel(endereco);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@Operation(summary = "Deleta o endereço do cliente", description = "Deleta o endereço do cliente")
	@DeleteMapping("/{enderecoId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deletarEnderecoDoCliente(@PathVariable Long clienteId, @PathVariable Long enderecoId,
			@AuthenticationPrincipal ClienteAutenticado clienteAutenticado) {
		if (permissaoService.isNaoTemAutorizacaoDeAdministrador(clienteAutenticado, clienteId)) {
			throw new AcessoNegadoException(
					"Você não tem autorização para deletar o endereço de outroo cliente no sistema!");
		}

		cadastroClienteEnderecoService.deletarEnderecoDoCliente(clienteId, enderecoId);
	}

}
