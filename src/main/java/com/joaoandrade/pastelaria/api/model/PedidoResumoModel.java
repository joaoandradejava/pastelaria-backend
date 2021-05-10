package com.joaoandrade.pastelaria.api.model;

import java.time.LocalDateTime;

public class PedidoResumoModel {
	private Long id;
	private LocalDateTime data;
	private Double valorTotal;
	private String situacaoPedido;
	private PagamentoModel pagamento;

	public PedidoResumoModel() {
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

}
