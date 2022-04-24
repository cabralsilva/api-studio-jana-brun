package br.com.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.api.enums.StatusActiveEnum;
import br.com.api.enums.TypeOfCurrencyEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class PaymentMethodDTO {

	private Integer identifier;
	private String description;
	private String code;
	private TypeOfCurrencyEnum currencyType;
	private StatusActiveEnum status;
}