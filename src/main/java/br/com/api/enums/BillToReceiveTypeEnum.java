package br.com.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BillToReceiveTypeEnum {

	SINGLE("label.single"),
	LESSON_DEMO("label.lesson.single"),	
	LESSON_SINGLE("label.lesson.single"),
	MATRICULATION("label.matriculation"),
	PRESENTATION("label.presentation"),
	SALE("label.sale");

	private final String i18n;
}
