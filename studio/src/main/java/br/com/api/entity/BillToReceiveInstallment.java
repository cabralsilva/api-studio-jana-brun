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

import br.com.api.enums.InstallmentStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bill_to_receive_installment")
@Getter
@Setter
public class BillToReceiveInstallment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bill_to_receive_installment_id")
	private Integer identifier;

	@Column(name = "bill_to_receive_installment_description", nullable = false)
	private String description;

	@Column(name = "bill_to_receive_installment_number", nullable = false)
	private Integer number;

	@ManyToOne
	@JoinColumn(name = "bill_to_receive_id", nullable = false)
	private BillToReceive billToReceive;

	@Enumerated(EnumType.STRING)
	@Column(name = "bill_to_receive_installment_status", nullable = false)
	private InstallmentStatusEnum status;

	@CreationTimestamp
	@Column(name = "bill_to_receive_installment_creation_date_time", nullable = false, updatable = false)
	private LocalDateTime creationDateTime;

	@Column(name = "bill_to_receive_installment_target_date", nullable = false)
	private LocalDateTime targetDate;

	@Column(name = "bill_to_receive_installment_value", nullable = false)
	private Double value;
}
