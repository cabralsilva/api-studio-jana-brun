package br.com.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypeOfValueEnum {

	INTEGER("label.integer"), 
	STRING("label.string"), 
	DATE("label.date");

	private final String i18n;
}
