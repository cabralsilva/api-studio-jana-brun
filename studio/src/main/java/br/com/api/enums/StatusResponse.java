package br.com.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusResponse {

	SUCCESS("label.status.response.success"), 
	ERROR("label.status.response.error");

	private final String i18n;
}
