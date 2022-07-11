package br.com.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WeekDayEnum {

	SUNDAY("label.week.day.sunday"), 
	MONDAY("label.week.day.monday"), 
	TUESDAY("label.week.day.tuesday"), 
	WEDNESDAY("label.week.day.wednesday"), 
	THURSDAY("label.week.day.thursday"), 
	FRIDAY("label.week.day.friday"), 
	SATURDAY("label.week.day.saturday");

	private final String i18n;
}
