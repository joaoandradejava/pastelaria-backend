package com.joaoandrade.pastelaria.domain.model;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.joaoandrade.pastelaria.domain.model.enumeration.SituacaoPagamento;

@Embeddable
public class Pagamento {

	@Enumerated(EnumType.STRING)
	private SituacaoPagamento situacaoPagamento;

	public Pagamento() {
	}

	public Pagamento(SituacaoPagamento situacaoPagamento) {
		this.situacaoPagamento = situacaoPagamento;
	}

	public SituacaoPagamento getSituacaoPagamento() {
		return situacaoPagamento;
	}

	public void setSituacaoPagamento(SituacaoPagamento situacaoPagamento) {
		this.situacaoPagamento = situacaoPagamento;
	}

}
