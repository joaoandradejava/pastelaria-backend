package com.joaoandrade.pastelaria.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joaoandrade.pastelaria.api.model.PedidoResumoModel;
import com.joaoandrade.pastelaria.domain.model.Pedido;

@Component
public class PedidoResumoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public PedidoResumoModel toModel(Pedido pedido) {
		return modelMapper.map(pedido, PedidoResumoModel.class);
	}
}
