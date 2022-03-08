package br.com.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NoticeTypeEnum {

	INFORMATION("label.information"), 
	WARNING("label.warning"), 
	IMPORTANT("label.important");

	private final String i18n;
}
