package com.joaoandrade.pastelaria.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.joaoandrade.pastelaria.api.model.ProdutoAdminModel;
import com.joaoandrade.pastelaria.domain.model.Produto;

@Component
public class ProdutoAdminModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public ProdutoAdminModel toModel(Produto produto) {
		ProdutoAdminModel produtoAdminModel = modelMapper.map(produto, ProdutoAdminModel.class);
		produtoAdminModel.setIsTemFoto(StringUtils.hasLength(produto.getAvatarUrl()) ? true : false);

		return modelMapper.map(produto, ProdutoAdminModel.class);
	}

	public List<ProdutoAdminModel> toCollectionModel(List<Produto> lista) {
		return lista.stream().map(produto -> toModel(produto)).collect(Collectors.toList());
	}

}
