package br.com.api.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.api.enums.StatusActiveEnum;
import br.com.api.enums.TypeOfCurrencyEnum;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payment_condition")
@Getter
@Setter
public class PaymentCondition {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_condition_id")
	private Integer identifier;

	@Column(name = "payment_condition_description")
	private String description;

	@Column(name = "payment_condition_code")
	private String code;

	@Column(name = "payment_condition_quantity_installments")
	private Integer quantityInstallments;

	@Column(name = "payment_condition_addition_percent")
	private Double additionPercent;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "payment_condition_status")
	private StatusActiveEnum status;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "payment_condition_currency_type")
	private TypeOfCurrencyEnum currencyType;
	
	@ManyToOne
	@JoinColumn(name = "payment_method_default_id")
	private PaymentMethod paymentMethodDefault;

}
