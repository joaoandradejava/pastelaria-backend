package com.joaoandrade.pastelaria.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.joaoandrade.pastelaria.domain.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	@Query("select p from Pedido p where p.cliente.id = ?1")
	Page<Pedido> buscarTodosPedidosDoCliente(Long clienteId, Pageable pageable);

	@Query("select p from Pedido p where p.id = ?2 and p.cliente.id = ?1")
	Optional<Pedido> buscarPedidoDoCliente(Long clienteId, Long pedidoId);
}
