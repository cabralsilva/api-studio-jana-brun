package br.com.api.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import br.com.api.enums.PayrollStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payroll")
@Getter
@Setter
public class Payroll {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payroll_id")
	private Integer identifier;

	@Column(name = "payroll_description", nullable = false, unique = true)
	private String description;

	@Column(name = "payroll_init_date")
	private LocalDate initDate;

	@Column(name = "payroll_end_date")
	private LocalDate endDate;

	@CreationTimestamp
	@Column(name = "payroll_creation_date_time", nullable = false, updatable = false)
	private LocalDateTime creationDateTime;

	@Column(name = "payroll_target_date", nullable = false)
	private LocalDate targetDate;
	
	@OneToMany(mappedBy = "payroll")
    private List<PayrollDetail> payrollDetailList;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "payroll_status")
	private PayrollStatusEnum status;
}
