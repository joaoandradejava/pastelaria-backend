package com.joaoandrade.pastelaria.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaoandrade.pastelaria.domain.exception.NegocioException;
import com.joaoandrade.pastelaria.domain.exception.PedidoNaoEncontradoException;
import com.joaoandrade.pastelaria.domain.model.Pedido;
import com.joaoandrade.pastelaria.domain.model.enumeration.SituacaoPedido;
import com.joaoandrade.pastelaria.domain.repository.PedidoRepository;
import com.joaoandrade.pastelaria.domain.service.crud.CadastroClienteService;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;

	@Autowired
	private CadastroClienteService cadastroClienteService;

	public Page<Pedido> buscarTodosOsPedidosDoSistema(Pageable pageable) {
		return repository.findAll(pageable);
	}

	private Pedido buscarPedidoPorId(Long pedidoId) {
		return repository.findById(pedidoId).orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
	}

	public Page<Pedido> buscarTodosPedidosDoCliente(Long clienteId, Pageable pageable) {
		return repository.buscarTodosPedidosDoCliente(clienteId, pageable);
	}

	public Pedido buscarPedidoDoCliente(Long clienteId, Long pedidoId) {
		cadastroClienteService.buscarPorId(clienteId);
		buscarPedidoPorId(pedidoId);

		return repository.buscarPedidoDoCliente(clienteId, pedidoId).orElseThrow(() -> new NegocioException(
				String.format("O Pedido de id %d não estar associado com o Cliente de id %d", pedidoId, clienteId)));
	}

	@Transactional
	public Pedido fazerPedido(Pedido pedido) {
		return repository.save(pedido);
	}

	@Transactional
	public void cancelarPedido(Long clienteId, Long pedidoId) {
		Pedido pedido = buscarPedidoDoCliente(clienteId, pedidoId);

		if (!(pedido.getSituacaoPedido() == SituacaoPedido.FAZENDO_A_COMIDA)) {
			throw new NegocioException(String.format("Não é possivel mudar a situacao do pedido de '%s' para '%s'",
					pedido.getSituacaoPedido().getDescricao(), SituacaoPedido.CANCELADO.getDescricao()));
		}

		pedido.cancelarPedido();
	}

	@Transactional
	public void sairParaEntrega(Long pedidoId) {
		Pedido pedido = buscarPedidoPorId(pedidoId);

		if (!(pedido.getSituacaoPedido() == SituacaoPedido.FAZENDO_A_COMIDA)) {
			throw new NegocioException(String.format("Não é possivel mudar a situacao do pedido de '%s' para '%s'",
					pedido.getSituacaoPedido().getDescricao(), SituacaoPedido.SAIU_PARA_ENTREGA.getDescricao()));
		}

		pedido.sairParaEntrega();
	}

	@Transactional
	public void concluido(Long pedidoId) {
		Pedido pedido = buscarPedidoPorId(pedidoId);

		if (!(pedido.getSituacaoPedido() == SituacaoPedido.SAIU_PARA_ENTREGA)) {
			throw new NegocioException(String.format("Não é possivel mudar a situacao do pedido de '%s' para '%s'",
					pedido.getSituacaoPedido().getDescricao(), SituacaoPedido.CONCLUIDO.getDescricao()));
		}

		pedido.concluir();
	}

	@Transactional
	public void cancelarPedidoPorImprevisto(Long pedidoId) {
		Pedido pedido = buscarPedidoPorId(pedidoId);

		pedido.cancelarPedido();
	}

}
