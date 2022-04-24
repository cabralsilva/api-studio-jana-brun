package br.com.api.entity;

import java.time.LocalDateTime;

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

import org.hibernate.annotations.CreationTimestamp;

import br.com.api.enums.PaymentStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bill_to_pay_installment_payment")
@Getter
@Setter
public class BillToPayInstallmentPayment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bill_to_pay_installment_payment_id")
	private Integer identifier;

	@Column(name = "bill_to_pay_installment_payment_code", nullable = false)
	private String code;

	@ManyToOne
	@JoinColumn(name = "bill_to_pay_installment_id", nullable = false)
	private BillToPayInstallment installment;

	@ManyToOne
	@JoinColumn(name = "payment_method_id", nullable = false)
	private PaymentMethod method;

	@Enumerated(EnumType.STRING)
	@Column(name = "bill_to_pay_installment_payment_status", nullable = false)
	private PaymentStatusEnum status;

	@CreationTimestamp
	@Column(name = "bill_to_pay_installment_payment_creation_date_time", nullable = false, updatable = false)
	private LocalDateTime creationDateTime;

	@Column(name = "bill_to_pay_installment_payment_payment_date", nullable = false, updatable = false)
	private LocalDateTime paymentDate;

	@Column(name = "bill_to_pay_installment_payment_value", nullable = false)
	private Double value;
}
