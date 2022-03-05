package br.com.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EmployeeStatusEnum {

	IN_COMPANY("label.employee.status.in.company"), 
	LEFT_COMPANY("label.employee.status.left.company");

	private final String i18n;
}
