package br.com.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MatriculationStatusEnum {

	TEMPORARY("label.temperary"),
	PRE_REGISTER("label.pre.matriculation"), 
	EFFECTIVE("label.effective"), 
	CANCELLED("label.cancelled");

	private final String i18n;
}
