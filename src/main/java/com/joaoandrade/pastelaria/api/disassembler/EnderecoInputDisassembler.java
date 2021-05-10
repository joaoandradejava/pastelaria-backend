package com.joaoandrade.pastelaria.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joaoandrade.pastelaria.api.input.EnderecoInput;
import com.joaoandrade.pastelaria.domain.model.Endereco;

@Component
public class EnderecoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Endereco toDomainModel(EnderecoInput enderecoInput) {
		return modelMapper.map(enderecoInput, Endereco.class);
	}

	public void copyToDomainModel(EnderecoInput enderecoInput, Endereco endereco) {
		modelMapper.map(enderecoInput, endereco);
	}
}
