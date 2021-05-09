package com.joaoandrade.pastelaria.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.joaoandrade.pastelaria.domain.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	@Query("select c from Categoria c where lower(c.nome) like lower(concat('%', ?1, '%'))")
	Page<Categoria> buscarTodosPorPaginacaoENome(String nome, Pageable pageable);

}
