package com.joaoandrade.pastelaria.domain.service.email;

import com.joaoandrade.pastelaria.domain.model.Cliente;

public interface EnvioEmailService {

	public void enviarEmail(Cliente cliente, String titulo, CorpoDoEnvioDeEmail corpo);
}
