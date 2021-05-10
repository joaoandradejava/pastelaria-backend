package com.joaoandrade.pastelaria.core.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.joaoandrade.pastelaria.domain.model.Cliente;
import com.joaoandrade.pastelaria.domain.repository.ClienteRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ClienteRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Cliente> obj = repository.findByEmail(username);

		if (obj.isEmpty()) {
			throw new UsernameNotFoundException(username);
		}

		Cliente cliente = obj.get();
		return new ClienteAutenticado(cliente.getId(), cliente.getEmail(), cliente.getSenha(), cliente.isAdmin(),
				cliente.getFuncoes());
	}

}
