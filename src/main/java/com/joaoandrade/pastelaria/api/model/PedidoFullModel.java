package com.joaoandrade.pastelaria.api.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoFullModel {
	private Long id;
	private LocalDateTime data;
	private Double valorTotal;
	private String situacaoPedido;
	private PagamentoModel pagamento;
	private EnderecoModel enderecoDeEntrega;
	private List<ItemPedidoModel> itens = new ArrayList<>();
	private ClienteResumoModel cliente;

	public PedidoFullModel() {
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

	public String getSituacaoPedido() {
		return situacaoPedido;
	}

	public void setSituacaoPedido(String situacaoPedido) {
		this.situacaoPedido = situacaoPedido;
	}

	public PagamentoModel getPagamento() {
		return pagamento;
	}

	public void setPagamento(PagamentoModel pagamento) {
		this.pagamento = pagamento;
	}

	public EnderecoModel getEnderecoDeEntrega() {
		return enderecoDeEntrega;
	}

	public void setEnderecoDeEntrega(EnderecoModel enderecoDeEntrega) {
		this.enderecoDeEntrega = enderecoDeEntrega;
	}

	public List<ItemPedidoModel> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedidoModel> itens) {
		this.itens = itens;
	}

	public ClienteResumoModel getCliente() {
		return cliente;
	}

	public void setCliente(ClienteResumoModel cliente) {
		this.cliente = cliente;
	}

}
