package com.joaoandrade.pastelaria.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.joaoandrade.pastelaria.domain.model.Cliente;
import com.joaoandrade.pastelaria.domain.observer.CadastroClienteObserver;
import com.joaoandrade.pastelaria.domain.observer.EsqueciASenhaObserver;
import com.joaoandrade.pastelaria.domain.service.email.CorpoDoEnvioDeEmail;
import com.joaoandrade.pastelaria.domain.service.email.EnvioEmailService;

@Component
public class EnvioEmailListener {

	@Autowired
	private EnvioEmailService envioEmailService;

	@EventListener
	public void cadastroClienteListener(CadastroClienteObserver cadastroClienteObserver) {
		Cliente cliente = cadastroClienteObserver.getCliente();
		envioEmailService.enviarEmail(cliente, "Cadastro realizado com  sucesso!",
				new CorpoDoEnvioDeEmail("cadastro-cliente.html", cliente));
	}

	@EventListener
	public void esqueciASenhaListener(EsqueciASenhaObserver esqueciASenhaObserver) {
		Cliente cliente = esqueciASenhaObserver.getCliente();
		envioEmailService.enviarEmail(cliente, "Senha alterada",
				new CorpoDoEnvioDeEmail("esqueci-senha.html", esqueciASenhaObserver));
	}
}
