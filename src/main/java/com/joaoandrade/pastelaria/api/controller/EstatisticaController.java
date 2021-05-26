package com.joaoandrade.pastelaria.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joaoandrade.pastelaria.domain.dto.ProdutoMaisVendidoDTO;
import com.joaoandrade.pastelaria.domain.dto.RelatorioDTO;
import com.joaoandrade.pastelaria.domain.service.EstatisticaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Estatistica Controller")
@RestController
@RequestMapping("/estatisticas")
public class EstatisticaController {

	@Autowired
	private EstatisticaService estatisticaService;

	@Operation(summary = "Busca os produtos mais vendidos - ADMIN", description = "Busca os produtos mais vendidos - ADMIN")
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/produtos-mais-vendidos")
	public Page<ProdutoMaisVendidoDTO> buscarOsProdutosMaisVendidos(@PageableDefault(size = 6) Pageable pageable) {
		Page<ProdutoMaisVendidoDTO> page = estatisticaService.buscarOsProdutosMaisVendidos(pageable);

		return page;
	}

	@Operation(summary = "Gera um relatorio dos produtos e a quantidade de vezes que foram vendidos - ADMIN", description = "Gera um relatorio dos produtos e a quantidade de vezes que foram vendidos - ADMIN")
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/produtos-mais-vendidos/relatorio")
	public RelatorioDTO gerarRelatorioDosProdutosMaisVendidos() {
		String relatorioEmBase64 = estatisticaService.gerarRelatorioDosProdutosMaisVendidos();

		return new RelatorioDTO(relatorioEmBase64);
	}
}
