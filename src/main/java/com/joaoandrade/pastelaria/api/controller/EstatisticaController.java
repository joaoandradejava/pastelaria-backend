package com.joaoandrade.pastelaria.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joaoandrade.pastelaria.domain.dto.ProdutoMaisVendidoDTO;
import com.joaoandrade.pastelaria.domain.service.EstatisticaService;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticaController {

	@Autowired
	private EstatisticaService estatisticaService;

	@GetMapping("/produtos-mais-vendidos")
	public Page<ProdutoMaisVendidoDTO> buscarOsProdutosMaisVendidos(@PageableDefault(size = 6) Pageable pageable) {
		Page<ProdutoMaisVendidoDTO> page = estatisticaService.buscarOsProdutosMaisVendidos(pageable);

		return page;
	}
}
