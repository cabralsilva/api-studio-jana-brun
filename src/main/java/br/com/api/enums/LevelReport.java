package br.com.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LevelReport {

	INFO("label.level.report.info"), WARN("label.level.report.warn"), FAILURE("label.level.report.failure"),
	ERROR("label.level.report.error");

	private final String i18n;
}
