package com.joaoandrade.pastelaria.api.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EnderecoInput {

	@Size(max = 11)
	@NotBlank
	private String cep;

	@Size(max = 255)
	@NotBlank
	private String endereco;

	@Size(max = 10)
	@NotBlank
	private String numero;

	@Size(max = 255)
	private String complemento;

	@Size(max = 255)
	@NotBlank
	private String bairro;

	@Size(max = 255)
	@NotBlank
	private String cidade;

	@Valid
	@NotNull
	private EstadoIdInput estado;

	public EnderecoInput() {
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public EstadoIdInput getEstado() {
		return estado;
	}

	public void setEstado(EstadoIdInput estado) {
		this.estado = estado;
	}

}
