package br.com.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatusEnum {

	OPEN("label.open"),
	AWAITING_PAYMENT("label.pre.matriculation"), 
	DONE("label.effective"), 
	CANCELLED("label.cancelled");

	private final String i18n;
}
