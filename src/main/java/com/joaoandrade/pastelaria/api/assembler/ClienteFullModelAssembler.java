package com.joaoandrade.pastelaria.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joaoandrade.pastelaria.api.model.ClienteFullModel;
import com.joaoandrade.pastelaria.domain.model.Cliente;

@Component
public class ClienteFullModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public ClienteFullModel toModel(Cliente cliente) {
		ClienteFullModel clienteFullModel = modelMapper.map(cliente, ClienteFullModel.class);
		clienteFullModel.setIsAdmin(cliente.isAdmin());

		return clienteFullModel;
	}

	public List<ClienteFullModel> toCollectionModel(List<Cliente> lista) {
		return lista.stream().map(cliente -> toModel(cliente)).collect(Collectors.toList());
	}
}
