package com.joaoandrade.pastelaria.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_EMPTY)
public class ProblemDetail {
	private LocalDateTime timestamp = LocalDateTime.now();
	private String type;
	private String title;
	private int status;
	private String detail;
	private String userMessage;
	private List<FieldMessage> errors = new ArrayList<>();

	public ProblemDetail() {
	}

	public ProblemDetail(String type, String title, int status, String detail, String userMessage) {
		this.type = type;
		this.title = title;
		this.status = status;
		this.detail = detail;
		this.userMessage = userMessage;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void adicionarError(String field, String userMessage) {
		this.errors.add(new FieldMessage(field, userMessage));
	}

}
