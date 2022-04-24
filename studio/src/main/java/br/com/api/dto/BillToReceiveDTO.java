package br.com.api.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.api.entity.PaymentCondition;
import br.com.api.entity.Person;
import br.com.api.enums.StatusOfBillEnum;
import br.com.api.enums.BillToReceiveTypeEnum;
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
	private PaymentCondition paymentCondition;
	private Person responsible;
	private BillToReceiveTypeEnum type;
	private StatusOfBillEnum status;
	private LocalDateTime creationDateTime;
	private LocalDateTime emissionDateTime;
	private Double value;
}
