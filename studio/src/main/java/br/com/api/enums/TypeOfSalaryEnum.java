package br.com.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypeOfSalaryEnum {

	BY_HOUR("label.by.hour"), 
	BY_PERCENT("label.by.percent"), 
	BY_MONTH("label.by.month");

	private final String i18n;
}
