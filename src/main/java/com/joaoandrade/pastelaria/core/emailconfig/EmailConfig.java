package com.joaoandrade.pastelaria.core.emailconfig;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.joaoandrade.pastelaria.domain.service.email.EnvioEmailService;
import com.joaoandrade.pastelaria.domain.service.email.SMTPEnvioEmailServiceImpl;
import com.joaoandrade.pastelaria.domain.service.email.SandboxEnvioEmailServiceImpl;

@Configuration
public class EmailConfig {

	@Autowired
	private EmailConfigProperty emailConfigProperty;

	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(emailConfigProperty.getHost());
		mailSender.setPort(emailConfigProperty.getPorta());

		mailSender.setUsername(emailConfigProperty.getEmail());
		mailSender.setPassword(emailConfigProperty.getSenha());

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		return mailSender;
	}

	@Bean
	public EnvioEmailService envioEmailService() {
		if (emailConfigProperty.getTipoEnvioEmail() == null
				|| emailConfigProperty.getTipoEnvioEmail() == TipoEnvioEmail.SMTP) {
			return new SMTPEnvioEmailServiceImpl();
		}

		return new SandboxEnvioEmailServiceImpl();
	}
}
