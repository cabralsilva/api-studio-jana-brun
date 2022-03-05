package br.com.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GenreEnum {

	NA("label.na"), MALE("label.male"), FEMALE("label.female");

	private final String i18n;
}
