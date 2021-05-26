package com.joaoandrade.pastelaria.domain.service.report;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class JasperRelatorioServiceImpl implements RelatorioService {

	@Override
	public String gerarRelatorio(String nomeRelatorio, Map<String, Object> parametros, List<?> lista) throws Exception {
		InputStream caminhoRelatorio = getClass().getResourceAsStream("/relatorios/" + nomeRelatorio + ".jasper");

		JRBeanCollectionDataSource connection = new JRBeanCollectionDataSource(lista);

		JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoRelatorio, parametros, connection);

		return "data:application/pdf;base64,"
				+ Base64.encodeBase64String(JasperExportManager.exportReportToPdf(jasperPrint));
	}

}
