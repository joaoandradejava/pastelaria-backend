package com.joaoandrade.pastelaria.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.joaoandrade.pastelaria.domain.model.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

	@Query("select e from Endereco e where e.id = ?2 and e.cliente.id = ?1")
	Optional<Endereco> buscarEnderecoDoCliente(Long clienteId, Long enderecoId);
}
