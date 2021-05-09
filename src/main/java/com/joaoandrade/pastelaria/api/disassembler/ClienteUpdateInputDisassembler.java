package com.joaoandrade.pastelaria.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joaoandrade.pastelaria.api.input.ClienteUpdateInput;
import com.joaoandrade.pastelaria.domain.model.Cliente;

@Component
public class ClienteUpdateInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public void copyToDomainModel(ClienteUpdateInput clienteUpdateInput, Cliente cliente) {
		modelMapper.map(clienteUpdateInput, cliente);
	}
}
