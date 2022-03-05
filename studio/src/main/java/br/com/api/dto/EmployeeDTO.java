package br.com.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.api.enums.EmployeeStatusEnum;
import br.com.api.enums.JobEnum;
import br.com.api.enums.TypeOfSalaryEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class EmployeeDTO {

	private Integer identifier;
	private IndividualDTO individual;
	private Boolean medicinContinuous;
	private String medicinNotes;
	private Boolean allergiesContinuous;
	private String allergiesNotes;
	private String email;
	private String phone1;
	private String phone2;
	private String instagram;
	private String facebook;
	private JobEnum job;
	private TypeOfSalaryEnum typeOfSalary;
	private EmployeeStatusEnum status;
	private Double salaryValue;
	private String password;
}
