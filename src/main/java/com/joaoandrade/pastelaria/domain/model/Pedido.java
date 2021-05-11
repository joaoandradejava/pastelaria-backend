package com.joaoandrade.pastelaria.domain.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import com.joaoandrade.pastelaria.domain.model.enumeration.SituacaoPagamento;
import com.joaoandrade.pastelaria.domain.model.enumeration.SituacaoPedido;

@Entity
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreationTimestamp
	private LocalDateTime data;
	private Double valorTotal;
	@Enumerated(EnumType.STRING)
	private SituacaoPedido situacaoPedido;

	@Embedded
	private Pagamento pagamento;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "endereco_de_entrega_id")
	private Endereco enderecoDeEntrega;

	@OneToMany(mappedBy = "id.pedido", cascade = CascadeType.ALL)
	private Set<ItemPedido> itens = new HashSet<>();

	public Pedido() {
	}

	public Pedido(Long id, LocalDateTime data, Double valorTotal, SituacaoPedido situacaoPedido, Pagamento pagamento,
			Cliente cliente, Endereco enderecoDeEntrega) {
		this.id = id;
		this.data = data;
		this.valorTotal = valorTotal;
		this.situacaoPedido = situacaoPedido;
		this.pagamento = pagamento;
		this.cliente = cliente;
		this.enderecoDeEntrega = enderecoDeEntrega;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public SituacaoPedido getSituacaoPedido() {
		return situacaoPedido;
	}

	public void setSituacaoPedido(SituacaoPedido situacaoPedido) {
		this.situacaoPedido = situacaoPedido;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEnderecoDeEntrega() {
		return enderecoDeEntrega;
	}

	public void setEnderecoDeEntrega(Endereco enderecoDeEntrega) {
		this.enderecoDeEntrega = enderecoDeEntrega;
	}

	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}

	public void adicionarItemPedido(ItemPedido itemPedido) {
		getItens().add(itemPedido);
	}

	public void calcularValorTotal() {
		double valorTotal = 0.0;
		for (ItemPedido itens : this.itens) {
			valorTotal += itens.calcularValorTotalDoItemPedido();
		}

		this.valorTotal = valorTotal;
	}

	public void cancelarPedido() {
		setSituacaoPedido(SituacaoPedido.CANCELADO);
		getPagamento().setSituacaoPagamento(SituacaoPagamento.CANCELADO);
	}

	public void sairParaEntrega() {
		setSituacaoPedido(SituacaoPedido.SAIU_PARA_ENTREGA);
	}

	public void concluir() {
		setSituacaoPedido(SituacaoPedido.CONCLUIDO);
		getPagamento().setSituacaoPagamento(SituacaoPagamento.QUITADO);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
