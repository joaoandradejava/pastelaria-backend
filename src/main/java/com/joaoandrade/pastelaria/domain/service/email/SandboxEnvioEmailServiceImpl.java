package com.joaoandrade.pastelaria.domain.service.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.MimeMessageHelper;

import com.joaoandrade.pastelaria.domain.exception.PrepararMimeMessageException;
import com.joaoandrade.pastelaria.domain.model.Cliente;

public class SandboxEnvioEmailServiceImpl extends AbstractEnvioEmailService {

	@Override
	public void enviarEmail(Cliente cliente, String titulo, CorpoDoEnvioDeEmail corpo) {
		try {
			MimeMessage mimeMessage = prepararMimeMessage(cliente, titulo, corpo);
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
			mimeMessageHelper.setTo(emailConfigProperty.getEmailSandbox());
			javaMailSender.send(mimeMessage);
		} catch (MessagingException e) {
			throw new PrepararMimeMessageException(
					"Ocorreu um erro inesperado ao tentar preparar o MimeMessage do envio de email");
		}
	}

}
