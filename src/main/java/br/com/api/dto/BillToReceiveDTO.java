package br.com.api.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.api.enums.BillToReceiveTypeEnum;
import br.com.api.enums.StatusOfBillEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class BillToReceiveDTO {

	private Integer identifier;
	private String title;
	private PaymentConditionDTO paymentCondition;
	private PersonDTO customer;
	private BillToReceiveTypeEnum type;
	private StatusOfBillEnum status;
	private LocalDateTime creationDateTime;
	private LocalDate emissionDate;
	private Double value;
	private MatriculationDTO matriculation;
	private OrderDTO order;
	private List<BillToReceiveInstallmentDTO> installmentList;
}
