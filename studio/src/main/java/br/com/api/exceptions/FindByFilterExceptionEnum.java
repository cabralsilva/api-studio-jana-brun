package br.com.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FindByFilterExceptionEnum {

	MORE_THAN_ONE_MATRICULATION_FOUND("00.001", "error.message.more.than.one.matriculation.found"),
	MATRICULATION_NOT_FOUND("00.002", "error.message.more.than.one.matriculation.found"),
	RESOURCE_NOT_FOUND("00.003", "error.message.resource.not.found");
	
	private final String code;
	private final String i18n;

}
