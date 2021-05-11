package com.joaoandrade.pastelaria.domain.service.email;

public class CorpoDoEnvioDeEmail {

	private final String nomeDoTemplateHtml;
	private final Object objetoDeEnvioDeEmail;

	public CorpoDoEnvioDeEmail(String nomeDoTemplateHtml, Object objetoDeEnvioDeEmail) {
		this.nomeDoTemplateHtml = nomeDoTemplateHtml;
		this.objetoDeEnvioDeEmail = objetoDeEnvioDeEmail;
	}

	public String getNomeDoTemplateHtml() {
		return nomeDoTemplateHtml;
	}

	public Object getObjetoDeEnvioDeEmail() {
		return objetoDeEnvioDeEmail;
	}

}