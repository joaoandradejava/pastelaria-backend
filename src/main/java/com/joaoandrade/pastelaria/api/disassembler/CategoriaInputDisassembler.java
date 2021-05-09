package com.joaoandrade.pastelaria.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joaoandrade.pastelaria.api.input.CategoriaInput;
import com.joaoandrade.pastelaria.domain.model.Categoria;

@Component
public class CategoriaInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Categoria toDomainModel(CategoriaInput categoriaInput) {
		return modelMapper.map(categoriaInput, Categoria.class);
	}

	public void copyToDomainModel(CategoriaInput categoriaInput, Categoria categoria) {
		modelMapper.map(categoriaInput, categoria);
	}
}
