package com.joaoandrade.pastelaria.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.joaoandrade.pastelaria.domain.dto.ProdutoMaisVendidoDTO;
import com.joaoandrade.pastelaria.domain.model.ItemPedido;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

	@Query(value = "select new com.joaoandrade.pastelaria.domain.dto.ProdutoMaisVendidoDTO(p.id, p.nome, sum(item.quantidade) as totalVendido) from ItemPedido as item join Produto as p on item.id.produto.id = p.id join Pedido as ped on item.id.pedido.id = ped.id where ped.situacaoPedido = 'CONCLUIDO' group by p.id order by totalVendido desc")
	public Page<ProdutoMaisVendidoDTO> buscarProdutosMaisVendidos(Pageable pageable);

	@Query(value = "select new com.joaoandrade.pastelaria.domain.dto.ProdutoMaisVendidoDTO(p.id, p.nome, sum(item.quantidade) as totalVendido) from ItemPedido as item join Produto as p on item.id.produto.id = p.id join Pedido as ped on item.id.pedido.id = ped.id where ped.situacaoPedido = 'CONCLUIDO' group by p.id order by totalVendido desc")
	public List<ProdutoMaisVendidoDTO> buscarProdutosMaisVendidos();

}
