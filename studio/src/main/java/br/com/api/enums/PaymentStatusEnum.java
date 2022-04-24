package br.com.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentStatusEnum {

	PROCESSING("label.processing"),
	CANCELLED("label.cancelled"),
	SETTLED("label.settled"),
	REFUND("label.refund"),
	PROVISIONED("label.bill.to.receive.status.provisioned");

	private final String i18n;
}
