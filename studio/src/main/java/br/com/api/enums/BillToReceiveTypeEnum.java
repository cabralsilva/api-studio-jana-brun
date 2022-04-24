package br.com.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BillToReceiveTypeEnum {

	LESSON_SINGLE("label.lesson.single"),
	matriculation("label.matriculation"),
	PRESENTATION("label.presentation"),
	STORE("label.store");

	private final String i18n;
}
