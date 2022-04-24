package br.com.api.dto;

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
public class BillToPayInstallmentDTO {

	private Integer identifier;
	private String description;
	private Integer number;
	private BillToPayDTO billToPay;
	private InstallmentStatusEnum status;
	private LocalDateTime creationDateTime;
	private LocalDateTime targetDate;
	private Double value;
}
