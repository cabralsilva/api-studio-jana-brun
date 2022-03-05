package br.com.api.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {

	private Integer identifier;
	private StudentDTO student;
	private IndividualDTO responsibleFinancial;
	private LocalDateTime creationDateTime;
	private LocalDateTime effectiveDateTime;
	private Integer monthDayToPayment;
    private List<ClassDTO> classList;

}
