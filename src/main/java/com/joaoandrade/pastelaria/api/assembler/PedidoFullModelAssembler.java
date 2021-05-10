package com.joaoandrade.pastelaria.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joaoandrade.pastelaria.api.model.PedidoFullModel;
import com.joaoandrade.pastelaria.domain.model.Pedido;

@Component
public class PedidoFullModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public PedidoFullModel toModel(Pedido pedido) {
		return modelMapper.map(pedido, PedidoFullModel.class);
	}
}
