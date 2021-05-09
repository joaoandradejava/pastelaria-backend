package com.joaoandrade.pastelaria.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.joaoandrade.pastelaria.domain.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	@Query("select c from Cliente c where lower(c.nome) like lower(concat('%', ?1, '%'))")
	Page<Cliente> buscarTodosPorPaginacaoENome(String nome, Pageable pageable);

	Optional<Cliente> findByEmail(String email);

	Optional<Cliente> findByCpf(String cpf);

}
