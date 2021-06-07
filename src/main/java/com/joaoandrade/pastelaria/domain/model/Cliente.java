package com.joaoandrade.pastelaria.domain.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.joaoandrade.pastelaria.domain.model.enumeration.Funcao;

@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String cpf;
	private String email;
	private String senha;
	private String telefone;
	private String celular;
	private Boolean isContaAtiva = Boolean.TRUE;

	@CollectionTable(name = "funcao")
	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private Set<Funcao> funcoes = new HashSet<>();

	@OneToMany(mappedBy = "cliente", orphanRemoval = true)
	private List<Endereco> enderecos = new ArrayList<>();

	@OneToMany(mappedBy = "cliente", orphanRemoval = true)
	private List<Pedido> pedidos = new ArrayList<>();

	public Cliente() {
		adicionarFuncao(Funcao.CLIENTE);
	}

	public Cliente(Long id, String nome, String cpf, String email, String senha, String telefone, String celular,
			Boolean isContaAtiva) {
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.senha = senha;
		this.telefone = telefone;
		this.celular = celular;
		this.isContaAtiva = isContaAtiva;
		adicionarFuncao(Funcao.CLIENTE);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public Boolean getIsContaAtiva() {
		return isContaAtiva;
	}

	public void setIsContaAtiva(Boolean isContaAtiva) {
		this.isContaAtiva = isContaAtiva;
	}

	public Set<Funcao> getFuncoes() {
		return funcoes;
	}

	public void setFuncoes(Set<Funcao> funcoes) {
		this.funcoes = funcoes;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public boolean isAdmin() {
		return funcoes.contains(Funcao.ADMIN);
	}

	public void adicionarFuncao(Funcao funcao) {
		this.funcoes.add(funcao);
	}

	public void removerFuncao(Funcao funcao) {
		this.funcoes.remove(funcao);
	}

	public void ativarConta() {
		setIsContaAtiva(true);

	}

	public void desativarConta() {
		setIsContaAtiva(false);

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
