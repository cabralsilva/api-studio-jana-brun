package br.com.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FindByFilterExceptionEnum {

	MORE_THAN_ONE_REGISTER_FOUND("00.001", "error.message.more.than.one.register.found");
	
	private final String code;
	private final String i18n;

}
