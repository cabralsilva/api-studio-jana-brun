package br.com.api.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.api.enums.EmployeeStatusEnum;
import br.com.api.enums.JobEnum;
import br.com.api.enums.RoleEnum;
import br.com.api.enums.TypeOfPaymentEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(of = "identifier")
public class EmployeeDTO implements Serializable {

	private static final long serialVersionUID = 3415837869632911853L;
	private Integer identifier;
	private PersonDTO person;
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
	private TypeOfPaymentEnum typeOfSalary;
	private EmployeeStatusEnum status;
	private Double salaryValue;
	private String password;
	private LocalDate creationDate;
	private LocalDate admissionDate;
	private LocalDate demissionDate;
	private List<RoleEnum> roleList;
	
	private String newPassword;
}
