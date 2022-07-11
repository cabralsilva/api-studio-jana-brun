package br.com.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProductCategoryEnum {

	LESSON("label.lesson"), TICKETS("label.tickets"), OTHER("label.other");

	private final String i18n;
}
