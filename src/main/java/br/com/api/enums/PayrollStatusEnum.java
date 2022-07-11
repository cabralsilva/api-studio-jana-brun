package br.com.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PayrollStatusEnum {

	PROCESSING("label.processing"),
	AWAITING_APPROVE("label.awaiting.approve"),
	CANCELLED("label.cancelled"),
	CONFIRMED("label.confirmed");

	private final String i18n;
}
