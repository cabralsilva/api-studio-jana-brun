package br.com.api.entity;

import java.time.LocalDate;
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
import org.hibernate.annotations.NaturalId;

import br.com.api.enums.StatusOfBillEnum;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bill_to_pay")
@Getter
@Setter
public class BillToPay {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bill_to_pay_id")
	private Integer identifier;

	@NaturalId
	@Column(name = "bill_to_pay_title", nullable = false, unique = true)
	private String title;

	@ManyToOne
	@JoinColumn(name = "payment_condition_id")
	private PaymentCondition paymentCondition;

	@ManyToOne
	@JoinColumn(name = "person_id")
	private Person person;

	@Enumerated(EnumType.STRING)
	@Column(name = "bill_to_pay_status", nullable = false)
	private StatusOfBillEnum status;

	@CreationTimestamp
	@Column(name = "bill_to_pay_creation_date_time", nullable = false, updatable = false)
	private LocalDateTime creationDateTime;

	@Column(name = "bill_to_pay_emission_date", nullable = false)
	private LocalDate emissionDate;

	@Column(name = "bill_to_pay_value", nullable = false)
	private Double value;

	@Column(name = "bill_to_pay_observation")
	private String observation;

}
