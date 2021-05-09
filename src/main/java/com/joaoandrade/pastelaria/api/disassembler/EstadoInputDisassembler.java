package com.joaoandrade.pastelaria.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joaoandrade.pastelaria.api.input.EstadoInput;
import com.joaoandrade.pastelaria.domain.model.Estado;

@Component
public class EstadoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Estado toDomainModel(EstadoInput estadoInput) {
		return modelMapper.map(estadoInput, Estado.class);
	}

	public void copyToDomainModel(EstadoInput estadoInput, Estado estado) {
		modelMapper.map(estadoInput, estado);
	}
}
