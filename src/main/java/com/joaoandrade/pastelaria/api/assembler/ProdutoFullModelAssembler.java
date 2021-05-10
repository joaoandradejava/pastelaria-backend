package com.joaoandrade.pastelaria.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joaoandrade.pastelaria.api.model.ProdutoFullModel;
import com.joaoandrade.pastelaria.domain.model.Produto;

@Component
public class ProdutoFullModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public ProdutoFullModel toModel(Produto produto) {
		return modelMapper.map(produto, ProdutoFullModel.class);
	}

	public List<ProdutoFullModel> toCollectionModel(List<Produto> lista) {
		return lista.stream().map(produto -> toModel(produto)).collect(Collectors.toList());
	}
}
