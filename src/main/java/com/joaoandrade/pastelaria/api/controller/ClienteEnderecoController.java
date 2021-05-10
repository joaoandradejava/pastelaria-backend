package com.joaoandrade.pastelaria.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.joaoandrade.pastelaria.domain.exception.EstadoNaoEncontradoException;
import com.joaoandrade.pastelaria.domain.exception.NegocioException;
import com.joaoandrade.pastelaria.domain.model.Cliente;
import com.joaoandrade.pastelaria.domain.model.Endereco;
import com.joaoandrade.pastelaria.domain.service.crud.CadastroClienteEnderecoService;
import com.joaoandrade.pastelaria.domain.service.crud.CadastroClienteService;

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

	@GetMapping
	public List<EnderecoModel> buscarTodos(@PathVariable Long clienteId) {
		Cliente cliente = cadastroClienteService.buscarPorId(clienteId);

		return enderecoModelAssembler.toCollectionModel(cliente.getEnderecos());
	}

	@GetMapping("/{enderecoId}")
	public EnderecoModel buscarEnderecoDoCliente(@PathVariable Long clienteId, @PathVariable Long enderecoId) {
		Endereco endereco = cadastroClienteEnderecoService.buscarEnderecoDoCliente(clienteId, enderecoId);

		return enderecoModelAssembler.toModel(endereco);
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public EnderecoModel cadastrar(@Valid @RequestBody EnderecoInput enderecoInput, @PathVariable Long clienteId) {
		try {
			Endereco endereco = cadastroClienteEnderecoService
					.cadastrar(enderecoInputDisassembler.toDomainModel(enderecoInput), clienteId);

			return enderecoModelAssembler.toModel(endereco);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@DeleteMapping("/{enderecoId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deletarEnderecoDoCliente(@PathVariable Long clienteId, @PathVariable Long enderecoId) {
		cadastroClienteEnderecoService.deletarEnderecoDoCliente(clienteId, enderecoId);
	}

}
