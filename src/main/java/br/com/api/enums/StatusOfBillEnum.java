package br.com.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusOfBillEnum {

	CREATED("label.created"),
	OPENED("label.opened"),
	PARTIALLY("label.partially"),
	CANCELLED("label.cancelled"),
	SETTLED("label.settled"),
	RENEGOTIATED("label.renegotiated"),
	REFUND("label.refund");

	private final String i18n;
}
