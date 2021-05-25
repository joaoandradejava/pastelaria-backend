package com.joaoandrade.pastelaria.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.joaoandrade.pastelaria.domain.dto.ProdutoMaisVendidoDTO;
import com.joaoandrade.pastelaria.domain.repository.ItemPedidoRepository;

@Service
public class EstatisticaService {

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public Page<ProdutoMaisVendidoDTO> buscarOsProdutosMaisVendidos(Pageable pageable) {
		return itemPedidoRepository.buscarProdutosMaisVendidos(pageable);
	}

}
