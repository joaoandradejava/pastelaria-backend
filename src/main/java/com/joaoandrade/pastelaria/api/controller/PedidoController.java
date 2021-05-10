package com.joaoandrade.pastelaria.api.controller;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

	@GetMapping
	public Page<PedidoResumoModel> buscarTodosPedidosDoCliente(
			@PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable,
			@AuthenticationPrincipal ClienteAutenticado clienteAutenticado) {
		Page<Pedido> page = pedidoService.buscarTodosPedidosDoCliente(clienteAutenticado.getId(), pageable);

		return page.map(pedido -> pedidoResumoModelAssembler.toModel(pedido));
	}

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
