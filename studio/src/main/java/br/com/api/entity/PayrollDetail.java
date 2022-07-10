package br.com.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payroll_detail")
@Getter
@Setter
public class PayrollDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payroll_detail_id")
	private Integer identifier;

	@Column(name = "payroll_detail_description", nullable = false, unique = true)
	private String description;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;

	@Column(name = "payroll_detail_value", nullable = false)
	private Double value;
	
	@ManyToOne
	@JoinColumn(name = "payroll_id")
	private Payroll payroll;
}
