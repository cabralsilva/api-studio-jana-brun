package br.com.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RegisterStatusEnum {

	TEMPORARY("label.temperary"),
	PRE_REGISTER("label.pre.register"), 
	EFFECTIVE("label.effective"), 
	CANCELLED("label.cancelled");

	private final String i18n;
}
