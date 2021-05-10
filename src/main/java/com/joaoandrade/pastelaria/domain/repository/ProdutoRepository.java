package com.joaoandrade.pastelaria.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.joaoandrade.pastelaria.domain.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	@Query("select p from Produto p where lower(p.nome) like lower(concat('%', ?1, '%'))")
	Page<Produto> buscarTodosPorPaginacaoENome(String nome, Pageable pageable);

	@Query("select p from Produto p where p.isTemEstoque = true")
	Page<Produto> buscarTodosProdutosDisponiveisNoEstoque(Pageable pageable);

	@Query("select p from Produto p where p.isTemEstoque = true and lower(p.nome) like lower(concat('%', ?1, '%'))")
	Page<Produto> buscarTodosProdutosDisponiveisNoEstoqueEPorNome(String nome, Pageable pageable);

	@Query("select p from Produto p where p.isTemEstoque = true and p.id = ?1")
	Optional<Produto> buscarPorIdEDisponivelNoEstoque(Long id);
}
