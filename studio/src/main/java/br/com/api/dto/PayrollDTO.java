package br.com.api.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.api.enums.PayrollStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class PayrollDTO {

	private Integer identifier;
	private String description;
	private LocalDate initDate;
	private LocalDate endDate;
	private LocalDateTime creationDateTime;
	private LocalDate targetDate;
	private List<PayrollDetailDTO> payrollDetailList;
	private PayrollStatusEnum status;
}
