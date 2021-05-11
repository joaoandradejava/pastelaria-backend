package com.joaoandrade.pastelaria.core.emailconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("email")
public class EmailConfigProperty {

	/**
	 * Host do servidor do gmail smtp
	 */
	private String host;

	/**
	 * Porta do servidor do gmail smtp
	 */
	private int porta;

	/**
	 * Email do envio de email
	 */
	private String email;

	/**
	 * Senha do email do envio de email
	 */
	private String senha;

	/**
	 * Email do sandbox
	 */
	private String emailSandbox;

	/**
	 * Tipos de envio de email. SMTP(PRODUÇÃO), SANDBOX(DESENVOLVIMENTO)
	 */
	private TipoEnvioEmail tipoEnvioEmail;

	public EmailConfigProperty() {
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPorta() {
		return porta;
	}

	public void setPorta(int porta) {
		this.porta = porta;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmailSandbox() {
		return emailSandbox;
	}

	public void setEmailSandbox(String emailSandbox) {
		this.emailSandbox = emailSandbox;
	}

	public TipoEnvioEmail getTipoEnvioEmail() {
		return tipoEnvioEmail;
	}

	public void setTipoEnvioEmail(TipoEnvioEmail tipoEnvioEmail) {
		this.tipoEnvioEmail = tipoEnvioEmail;
	}

}
