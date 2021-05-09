package com.joaoandrade.pastelaria.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joaoandrade.pastelaria.api.model.ClienteModel;
import com.joaoandrade.pastelaria.domain.model.Cliente;

@Component
public class ClienteModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public ClienteModel toModel(Cliente cliente) {
		ClienteModel clienteModel = modelMapper.map(cliente, ClienteModel.class);
		clienteModel.setIsAdmin(cliente.isAdmin());

		return clienteModel;
	}

	public List<ClienteModel> toCollectionModel(List<Cliente> lista) {
		return lista.stream().map(cliente -> toModel(cliente)).collect(Collectors.toList());
	}
}
