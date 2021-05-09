package com.joaoandrade.pastelaria.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.joaoandrade.pastelaria.domain.model.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

	@Query("select e from Estado e where lower(e.nome) like lower(concat('%', ?1, '%'))")
	Page<Estado> buscarTodosPorPaginacaoENome(String nome, Pageable pageable);

}
