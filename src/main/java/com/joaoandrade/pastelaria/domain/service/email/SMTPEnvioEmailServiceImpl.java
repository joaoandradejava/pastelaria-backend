package com.joaoandrade.pastelaria.domain.service.email;

import javax.mail.internet.MimeMessage;

import com.joaoandrade.pastelaria.domain.model.Cliente;

public class SMTPEnvioEmailServiceImpl extends AbstractEnvioEmailService {

	@Override
	public void enviarEmail(Cliente cliente, String titulo, CorpoDoEnvioDeEmail corpo) {
		MimeMessage mimeMessage = prepararMimeMessage(cliente, titulo, corpo);
		javaMailSender.send(mimeMessage);

	}

}
