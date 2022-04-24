package br.com.api.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.api.entity.PaymentCondition;
import br.com.api.entity.Supplier;
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
public class BillToPayDTO {

	private Integer identifier;
	private String title;
	private PaymentCondition paymentCondition;
	private Supplier supplier;
	private StatusOfBillEnum status;
	private LocalDateTime creationDateTime;
	private LocalDateTime emissionDateTime;
	private Double value;
}
