package com.joaoandrade.pastelaria.core.openapiconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI().info(new Info().title("Pastelaria do Beiçola API").description(
				"API Da pastelaria do beiçola, uma das grandes pastelaria do mundo ficticio da grande familia trazida pro mundo real e virtual")
				.contact(new Contact().email("pastelariadobeicolaagostinho@gmail.com").name("GMAIL DE CONTATO"))
				.version("v1.0"));
	}
}
