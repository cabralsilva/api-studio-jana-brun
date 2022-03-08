package br.com.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypeOfPersonEnum {

	INDIVIDUAL("label.individual"), CORPORATE("label.corporate");

	private final String i18n;
}
