package com.joaoandrade.pastelaria.domain.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.joaoandrade.pastelaria.domain.exception.EntidadeNaoProcessavelException;

public enum FormatoImagem {
	IMAGEM("image/jpeg", "image/png");

	private List<String> formatos = new ArrayList<>();

	private FormatoImagem(String... formatos) {
		this.formatos = Arrays.asList(formatos);
	}

	public List<String> getFormatos() {
		return formatos;
	}

	public static boolean isFormatoImagem(String formato) {
		FormatoImagem formatoImagem = FormatoImagem.IMAGEM;
		for (String formatoImg : formatoImagem.getFormatos()) {
			if (formatoImg.equalsIgnoreCase(formato)) {
				return true;
			}
		}

		throw new EntidadeNaoProcessavelException("Oferecemos suporte apenas para imagens PNG ou JPG.");
	}
}
