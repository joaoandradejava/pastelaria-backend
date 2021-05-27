package com.joaoandrade.pastelaria.domain.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.joaoandrade.pastelaria.domain.dto.ProdutoMaisVendidoDTO;
import com.joaoandrade.pastelaria.domain.exception.ErroInternoNoServidorException;
import com.joaoandrade.pastelaria.domain.repository.ItemPedidoRepository;
import com.joaoandrade.pastelaria.domain.repository.ProdutoRepository;
import com.joaoandrade.pastelaria.domain.service.report.RelatorioService;

@Service
public class EstatisticaService {

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private RelatorioService relatorioService;

	public Page<ProdutoMaisVendidoDTO> buscarOsProdutosMaisVendidos(Pageable pageable) {
		return itemPedidoRepository.buscarProdutosMaisVendidos(pageable);
	}

	public String gerarRelatorioDosProdutosMaisVendidos() {
		try {
			Map<String, Object> parametros = new HashMap<>();
			String relatorioEmBase64 = relatorioService.gerarRelatorio("produtos-mais-vendidos", parametros,
					itemPedidoRepository.buscarProdutosMaisVendidos());

			return relatorioEmBase64;
		} catch (Exception e) {
			throw new ErroInternoNoServidorException(
					"Ocorreu um erro inesperado na geração de relatorio dos produtos mais vendidos");
		}
	}

	public String gerarRelatorioDosProdutos() {
		try {
			Map<String, Object> parametros = new HashMap<>();
			String relatorioEmBase64 = relatorioService.gerarRelatorio("produtos", parametros,
					produtoRepository.buscarTodosProdutoParaGerarRelatorio());

			return relatorioEmBase64;
		} catch (Exception e) {
			throw new ErroInternoNoServidorException(
					"Ocorreu um erro inesperado na geração de relatorio dos produtos mais vendidos");
		}
	}

}
