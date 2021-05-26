package com.joaoandrade.pastelaria.domain.service.report;

import java.util.List;
import java.util.Map;

public interface RelatorioService {

	public String gerarRelatorio(String nomeRelatorio, Map<String, Object> parametros, List<?> lista)
			throws Exception;
}
