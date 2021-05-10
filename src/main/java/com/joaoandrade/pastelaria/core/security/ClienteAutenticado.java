package com.joaoandrade.pastelaria.core.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.joaoandrade.pastelaria.domain.model.enumeration.Funcao;

public class ClienteAutenticado implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String email;
	private String senha;
	private Boolean isAdmin;
	private Collection<? extends GrantedAuthority> funcoes;

	public ClienteAutenticado() {
	}

	public ClienteAutenticado(Long id, String email, String senha, Boolean isAdmin, Set<Funcao> funcoes) {
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.isAdmin = isAdmin;
		this.funcoes = funcoes.stream().map(funcao -> new SimpleGrantedAuthority(funcao.getDescricao()))
				.collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return funcoes;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
