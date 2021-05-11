package com.joaoandrade.pastelaria.api.controller;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.joaoandrade.pastelaria.api.assembler.PedidoFullModelAssembler;
import com.joaoandrade.pastelaria.api.assembler.PedidoResumoModelAssembler;
import com.joaoandrade.pastelaria.api.input.ItemPedidoInput;
import com.joaoandrade.pastelaria.api.input.PedidoInput;
import com.joaoandrade.pastelaria.api.model.PedidoFullModel;
import com.joaoandrade.pastelaria.api.model.PedidoResumoModel;
import com.joaoandrade.pastelaria.core.security.ClienteAutenticado;
import com.joaoandrade.pastelaria.domain.exception.NegocioException;
import com.joaoandrade.pastelaria.domain.exception.ObjetoNaoEncontradoException;
import com.joaoandrade.pastelaria.domain.model.Cliente;
import com.joaoandrade.pastelaria.domain.model.Endereco;
import com.joaoandrade.pastelaria.domain.model.ItemPedido;
import com.joaoandrade.pastelaria.domain.model.ItemPedidoPk;
import com.joaoandrade.pastelaria.domain.model.Pagamento;
import com.joaoandrade.pastelaria.domain.model.Pedido;
import com.joaoandrade.pastelaria.domain.model.Produto;
import com.joaoandrade.pastelaria.domain.model.enumeration.SituacaoPagamento;
import com.joaoandrade.pastelaria.domain.model.enumeration.SituacaoPedido;
import com.joaoandrade.pastelaria.domain.service.PedidoService;
import com.joaoandrade.pastelaria.domain.service.crud.CadastroClienteEnderecoService;
import com.joaoandrade.pastelaria.domain.service.crud.CadastroClienteService;
import com.joaoandrade.pastelaria.domain.service.crud.CadastroProdutoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pedido Controller")
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private CadastroProdutoService cadastroProdutoService;

	@Autowired
	private CadastroClienteService cadastroClienteService;

	@Autowired
	private CadastroClienteEnderecoService cadastroClienteEnderecoService;

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private PedidoFullModelAssembler pedidoFullModelAssembler;

	@Autowired
	private PedidoResumoModelAssembler pedidoResumoModelAssembler;

	@Operation(summary = "Busca todos os pedidos do sistema - ADMIN", description = "Busca todos os pedidos do sistema - ADMIN")
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/buscar-todos/paginacao")
	public Page<PedidoResumoModel> buscarTodosOsPedidosDoSistema(
			@PageableDefault(sort = "data", direction = Direction.DESC) Pageable pageable) {
		Page<Pedido> page = pedidoService.buscarTodosOsPedidosDoSistema(pageable);

		return page.map(pedido -> pedidoResumoModelAssembler.toModel(pedido));
	}

	@Operation(summary = "Busca todos os pedidos do cliente", description = "Busca todos os pedidos do cliente")
	@GetMapping
	public Page<PedidoResumoModel> buscarTodosPedidosDoCliente(
			@PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable,
			@AuthenticationPrincipal ClienteAutenticado clienteAutenticado) {
		Page<Pedido> page = pedidoService.buscarTodosPedidosDoCliente(clienteAutenticado.getId(), pageable);

		return page.map(pedido -> pedidoResumoModelAssembler.toModel(pedido));
	}

	@Operation(summary = "Busca o pedido do cliente", description = "Busca o pedido do cliente")
	@GetMapping("/{pedidoId}")
	public PedidoFullModel buscarPedidoDoCliente(@AuthenticationPrincipal ClienteAutenticado clienteAutenticado,
			@PathVariable Long pedidoId) {
		Pedido pedido = pedidoService.buscarPedidoDoCliente(clienteAutenticado.getId(), pedidoId);

		return pedidoFullModelAssembler.toModel(pedido);
	}

	@Operation(summary = "Faz um novo pedido", description = "Faz um novo pedido")
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public PedidoFullModel fazerPedido(@Valid @RequestBody PedidoInput pedidoInput,
			@AuthenticationPrincipal ClienteAutenticado clienteAutenticado) {
		try {
			Pedido pedido = montarPedido(pedidoInput, clienteAutenticado);

			pedido = pedidoService.fazerPedido(pedido);

			return pedidoFullModelAssembler.toModel(pedido);
		} catch (ObjetoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@Operation(summary = "Cancela o pedido", description = "Cancela o pedido")
	@PutMapping("/{pedidoId}/cancelado")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void cancelarPedido(@PathVariable Long pedidoId,
			@AuthenticationPrincipal ClienteAutenticado clienteAutenticado) {
		pedidoService.cancelarPedido(clienteAutenticado.getId(), pedidoId);

	}

	@Operation(summary = "Saiu para a entrega pedido - ADMIN", description = "Saiu para a entrega pedido - ADMIN")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{pedidoId}/saiu-para-entrega")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void sairParaEntrega(@PathVariable Long pedidoId) {
		pedidoService.sairParaEntrega(pedidoId);
	}

	@Operation(summary = "Conclui o pedido - ADMIN", description = "Conclui o pedido - ADMIN")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{pedidoId}/concluido")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void concluido(@PathVariable Long pedidoId) {
		pedidoService.concluido(pedidoId);
	}

	@Operation(summary = "Cancela o pedido caso ocorra algum imprevisto - ADMIN", description = "Cancela o pedido caso ocorra algum imprevisto - ADMIN")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{pedidoId}/cancelado-imprevisto")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void cancelarPedidoPorImprevisto(@PathVariable Long pedidoId) {
		pedidoService.cancelarPedidoPorImprevisto(pedidoId);
	}

	private Pedido montarPedido(@Valid PedidoInput pedidoInput, ClienteAutenticado clienteAutenticado) {
		Cliente cliente = cadastroClienteService.buscarPorId(clienteAutenticado.getId());
		Endereco enderecoDeEntrega = cadastroClienteEnderecoService.buscarEnderecoDoCliente(cliente.getId(),
				pedidoInput.getEnderecoDeEntrega().getId());
		Pedido pedido = new Pedido(null, LocalDateTime.now(), 0.0, SituacaoPedido.FAZENDO_A_COMIDA,
				new Pagamento(SituacaoPagamento.PENDENTE), cliente, enderecoDeEntrega);
		for (ItemPedidoInput item : pedidoInput.getItens()) {
			Produto produto = cadastroProdutoService.buscarPorIdEDisponivelNoEstoque(item.getProdutoId());
			ItemPedido itemPedido = new ItemPedido(new ItemPedidoPk(pedido, produto), item.getQuantidade(),
					produto.getPreco(), produto.getDesconto());

			pedido.adicionarItemPedido(itemPedido);
		}

		pedido.calcularValorTotal();
		return pedido;
	}

}
