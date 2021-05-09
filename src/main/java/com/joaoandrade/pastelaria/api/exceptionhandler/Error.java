package com.joaoandrade.pastelaria.api.exceptionhandler;

public enum Error {
	NEGOCIO("negocio", "Ocorreu um erro do lado do client-side(frontend)"),
	RECURSO_NAO_ENCONTRADO("recurso-nao-encontrado", "Recurso não encontrado"),
	ENTIDADE_EM_USO("entidade-em-uso", "Entidade em Uso"),
	ERRO_DE_VALIDACAO_DOS_DADOS("erro-validacao", "Ocorreu um erro de validação dos dados"),
	ERRO_NA_DESSERIALIZACAO_JSON("erro-desserializacao-json", "Erro na desserializacao do JSON"),
	PROPRIEDADE_NAO_RECONHECIDA("propriedade-nao-reconhecida", "Propriedade não reconhecida"),
	METODO_NAO_SUPORTADO("metodo-nao-suportado", "Método não suportado"),
	TIPO_INCOPATIVEL("tipo-incopativel", "Tipo incopativel"),
	ERRO_INTERNO_NO_SERVIDOR("erro-interno-no-servidor", "Erro interno no servidor");

	private String type;
	private String title;

	private Error(String type, String title) {
		this.type = "https://www.pastelaria-joaoandrade.com.br/" + type;
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public String getTitle() {
		return title;
	}
}
