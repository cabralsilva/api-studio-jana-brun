package br.com.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypeOfEntityEnum {

	INDIVIDUAL("label.individual.entity"), CORPORATE("label.corporate.entity");

	private final String i18n;
}
