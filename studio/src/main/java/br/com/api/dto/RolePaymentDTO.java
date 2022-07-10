package br.com.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.api.enums.TypeOfPaymentEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class RolePaymentDTO {

	private Integer identifier;
	private EmployeeDTO employee;
	private int sinceStudentNumber;
	private int untilStudentNumber;
	private TypeOfPaymentEnum typeOfPayment;
	private Double paymentValue;
}
