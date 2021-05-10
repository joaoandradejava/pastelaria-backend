package com.joaoandrade.pastelaria.core.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("jwt")
public class JwtConfigProperty {

	/**
	 * Tempo de expiração do token JWT em milisegundos
	 */
	private Long tempoDeExpiracaoDoTokenJwt;

	/**
	 * Senha de criptografia do token JWT
	 */
	private String senhaDeCriptografiaDoTokenJwt;

	public JwtConfigProperty() {
	}

	public Long getTempoDeExpiracaoDoTokenJwt() {
		return tempoDeExpiracaoDoTokenJwt;
	}

	public void setTempoDeExpiracaoDoTokenJwt(Long tempoDeExpiracaoDoTokenJwt) {
		this.tempoDeExpiracaoDoTokenJwt = tempoDeExpiracaoDoTokenJwt;
	}

	public String getSenhaDeCriptografiaDoTokenJwt() {
		return senhaDeCriptografiaDoTokenJwt;
	}

	public void setSenhaDeCriptografiaDoTokenJwt(String senhaDeCriptografiaDoTokenJwt) {
		this.senhaDeCriptografiaDoTokenJwt = senhaDeCriptografiaDoTokenJwt;
	}

}
