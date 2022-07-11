package br.com.api.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.api.enums.InstallmentStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class BillToReceiveInstallmentDTO {

	private Integer identifier;
	private String description;
	private Integer number;
	private BillToReceiveDTO billToReceive;
	private InstallmentStatusEnum status;
	private LocalDateTime creationDateTime;
	private LocalDate targetDate;
	private Double originalValue;
	private Double additionValue;
	private Double value;
}
