package br.com.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusActiveEnum {

	ACTIVE("label.status.active"), INACTIVE("label.status.inactive");

	private final String i18n;
}
