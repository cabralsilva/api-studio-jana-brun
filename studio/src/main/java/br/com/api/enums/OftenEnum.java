package br.com.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OftenEnum {

	ONCE("label.often.once"), 
	DAILY("label.often.every.day"), 
	WEEKLY("label.often.every.week"), 
	MONTHLY("label.often.every.month");

	private final String i18n;
}
