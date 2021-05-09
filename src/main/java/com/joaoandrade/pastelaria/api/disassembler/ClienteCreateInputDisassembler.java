package com.joaoandrade.pastelaria.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joaoandrade.pastelaria.api.input.ClienteCreateInput;
import com.joaoandrade.pastelaria.domain.model.Cliente;

@Component
public class ClienteCreateInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Cliente toDomainModel(ClienteCreateInput clienteCreateInput) {
		return modelMapper.map(clienteCreateInput, Cliente.class);
	}
}
