package br.com.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum JobEnum {

	TEACHER("label.teacher"), 
	SECRETARY("label.secretary"), 
	FINANCIAL("label.financial"), 
	DIRECTOR("label.director");

	private final String i18n;
}
