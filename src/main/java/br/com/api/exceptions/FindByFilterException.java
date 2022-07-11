package br.com.api.exceptions;

import lombok.Getter;

@Getter
public class FindByFilterException extends Exception {

	private static final long serialVersionUID = 4804140288431217538L;

	private FindByFilterExceptionEnum error;

	public FindByFilterException(FindByFilterExceptionEnum error) {
		super(error.getI18n());
		this.error = error;
	}

	@Override
	public String getMessage() {
		return error.getI18n();
	}

	public String getCode() {
		return error.getCode();
	}

}
