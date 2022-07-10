package br.com.api.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;

import br.com.api.enums.BillToReceiveTypeEnum;
import br.com.api.enums.StatusOfBillEnum;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bill_to_receive")
@Getter
@Setter
public class BillToReceive {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bill_to_receive_id")
	private Integer identifier;

	@NaturalId
	@Column(name = "bill_to_receive_title", nullable = false, unique = true)
	private String title;

	@ManyToOne
	@JoinColumn(name = "payment_condition_id")
	private PaymentCondition paymentCondition;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Person customer;

	@Enumerated(EnumType.STRING)
	@Column(name = "bill_to_receive_type", nullable = false)
	private BillToReceiveTypeEnum type;

	@Enumerated(EnumType.STRING)
	@Column(name = "bill_to_receive_status", nullable = false)
	private StatusOfBillEnum status;

	@CreationTimestamp
	@Column(name = "bill_to_receive_creation_date_time", nullable = false, updatable = false)
	private LocalDateTime creationDateTime;

	@Column(name = "bill_to_receive_emission_date", nullable = false)
	private LocalDate emissionDate;

	@Column(name = "bill_to_receive_value", nullable = false)
	private Double value;
	
	@ManyToOne
	@JoinColumn(name="matriculation_id")
	private Matriculation matriculation;

	@ManyToOne
	@JoinColumn(name="order_id")
	private Order order;
}
