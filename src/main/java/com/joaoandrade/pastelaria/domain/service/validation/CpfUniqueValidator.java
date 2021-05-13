package com.joaoandrade.pastelaria.domain.service.validation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.joaoandrade.pastelaria.domain.exception.NegocioException;
import com.joaoandrade.pastelaria.domain.model.Cliente;
import com.joaoandrade.pastelaria.domain.repository.ClienteRepository;

@Component
public class CpfUniqueValidator {

	@Autowired
	private ClienteRepository repository;

	public void validarSeCpfEValido(Cliente cliente) {
		if (!StringUtils.hasLength(cliente.getCpf())) {
			return;
		}

		Optional<Cliente> obj = repository.findByCpf(cliente.getCpf());

		if (obj.isPresent() && !obj.get().equals(cliente)) {
			throw new NegocioException(
					String.format("Já existe um usuario cadastrado com o cpf '%s' no sistema!", cliente.getCpf()));
		}
	}
}
