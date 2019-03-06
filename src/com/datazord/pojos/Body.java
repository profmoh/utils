package com.datazord.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Body<T> {
	boolean success;
	String error;
	T body;

	@JsonProperty
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String token;

	public Body() {
		this.token = null;
	}

	public Body(String token) {
		this.token = token;
	}

	public Body(T body, String error, boolean success) {
		super();

		this.body = body;
		this.token = null;
		this.error = error;
		this.success = success;
	}

	public Body(T body, String error, boolean success, String token) {
		super();

		this.body = body;
		this.token = token;
		this.error = error;
		this.success = success;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
