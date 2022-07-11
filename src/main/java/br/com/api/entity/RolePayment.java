package br.com.api.entity;

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

import br.com.api.enums.TypeOfPaymentEnum;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "role_payment")
@Getter
@Setter
public class RolePayment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_payment_id")
	private Integer identifier;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;

	@Column(name = "role_payment_since_student_number")
	private int sinceStudentNumber;

	@Column(name = "role_payment_until_student_number")
	private int untilStudentNumber;

	@Enumerated(EnumType.STRING)
	@Column(name = "role_payment_type_of_payment")
	private TypeOfPaymentEnum typeOfPayment;

	@Column(name = "role_payment_payment_value")
	private Double paymentValue;
}
