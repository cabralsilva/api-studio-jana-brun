package br.com.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OftenEnum {

	ONCE("label.often.once"), 
	EVERY_DAY("label.often.every.day"), 
	EVERY_WEEK("label.often.every.week"), 
	EVERY_MONTH("label.often.every.month");

	private final String i18n;
}
