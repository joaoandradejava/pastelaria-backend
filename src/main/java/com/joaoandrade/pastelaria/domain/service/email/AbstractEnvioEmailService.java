package com.joaoandrade.pastelaria.domain.service.email;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.joaoandrade.pastelaria.core.emailconfig.EmailConfigProperty;
import com.joaoandrade.pastelaria.domain.exception.ErroInternoNoServidorException;
import com.joaoandrade.pastelaria.domain.exception.PrepararMimeMessageException;
import com.joaoandrade.pastelaria.domain.model.Cliente;

import freemarker.template.Configuration;
import freemarker.template.Template;

public abstract class AbstractEnvioEmailService implements EnvioEmailService {

	@Autowired
	protected JavaMailSender javaMailSender;

	@Autowired
	protected EmailConfigProperty emailConfigProperty;

	@Autowired
	private Configuration configuration;

	protected MimeMessage prepararMimeMessage(Cliente cliente, String titulo, CorpoDoEnvioDeEmail corpoDoEnvioDeEmail) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
			mimeMessageHelper.setSentDate(new Date());
			mimeMessageHelper.setFrom(emailConfigProperty.getEmail());
			mimeMessageHelper.setTo(cliente.getEmail());
			mimeMessageHelper.setSubject(titulo);
			mimeMessageHelper.setText(processarHtmlETransformarEmString(corpoDoEnvioDeEmail), true);

			return mimeMessage;
		} catch (MessagingException e) {
			throw new PrepararMimeMessageException(
					"Ocorreu um erro inesperado ao tentar preparar o MimeMessage do envio de email");
		}
	}

	private String processarHtmlETransformarEmString(CorpoDoEnvioDeEmail corpoDoEnvioDeEmail) {
		try {
			Template template = configuration.getTemplate(corpoDoEnvioDeEmail.getNomeDoTemplateHtml(),
					LocaleContextHolder.getLocale(), "UTF-8");

			return FreeMarkerTemplateUtils.processTemplateIntoString(template,
					corpoDoEnvioDeEmail.getObjetoDeEnvioDeEmail());
		} catch (Exception e) {
			throw new ErroInternoNoServidorException(
					"Ocorreu um erro inesperado ao tentar processar o HTML e transforma-lo em String para o Envio de Email");
		}
	}
}
