package br.com.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.api.enums.StatusActiveEnum;
import br.com.api.enums.TypeOfCurrencyEnum;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payment_method")
@Getter
@Setter
public class PaymentMethod {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_method_id")
	private Integer identifier;

	@Column(name = "payment_method_description")
	private String description;

	@Column(name = "payment_method_code")
	private String code;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "payment_method_currency_type")
	private TypeOfCurrencyEnum currencyType;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "payment_method_status")
	private StatusActiveEnum status;

}
