package com.joaoandrade.pastelaria.domain.service.validation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joaoandrade.pastelaria.domain.exception.NegocioException;
import com.joaoandrade.pastelaria.domain.model.Cliente;
import com.joaoandrade.pastelaria.domain.repository.ClienteRepository;

@Component
public class EmailUniqueValidator {

	@Autowired
	private ClienteRepository repository;

	public void validarSeEmailEValido(Cliente cliente) {
		Optional<Cliente> obj = repository.findByEmail(cliente.getEmail());

		if (obj.isPresent() && !obj.get().equals(cliente)) {
			throw new NegocioException(
					String.format("Já existe um usuario cadastrado com o email '%s' no sistema!", cliente.getEmail()));
		}
	}
}
