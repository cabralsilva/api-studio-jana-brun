package br.com.api.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.api.entity.BillToReceiveInstallment;
import br.com.api.entity.PaymentMethod;
import br.com.api.enums.PaymentStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class BillToReceiveInstallmentPaymentDTO {

	private Integer identifier;
	private String code;
	private BillToReceiveInstallment installment;
	private PaymentMethod method;
	private PaymentStatusEnum status;
	private LocalDateTime creationDateTime;
	private LocalDateTime paymentDate;
	private Double value;
}
