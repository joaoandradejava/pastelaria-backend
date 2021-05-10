package com.joaoandrade.pastelaria.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joaoandrade.pastelaria.api.input.ProdutoInput;
import com.joaoandrade.pastelaria.domain.model.Categoria;
import com.joaoandrade.pastelaria.domain.model.Produto;

@Component
public class ProdutoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Produto toDomainModel(ProdutoInput produtoInput) {
		return modelMapper.map(produtoInput, Produto.class);
	}

	public void copyToDomainModel(ProdutoInput produtoInput, Produto produto) {
		produto.setCategoria(new Categoria());

		modelMapper.map(produtoInput, produto);
	}
}
