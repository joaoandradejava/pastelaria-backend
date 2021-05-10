package com.joaoandrade.pastelaria.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaoandrade.pastelaria.domain.model.Pedido;
import com.joaoandrade.pastelaria.domain.repository.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;

	public Page<Pedido> buscarTodosPedidosDoCliente(Long clienteId, Pageable pageable) {
		return repository.buscarTodosPedidosDoCliente(clienteId, pageable);
	}

	@Transactional
	public Pedido fazerPedido(Pedido pedido) {
		return repository.save(pedido);
	}

}
