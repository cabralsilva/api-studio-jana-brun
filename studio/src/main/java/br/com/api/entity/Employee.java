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

import br.com.api.enums.EmployeeStatusEnum;
import br.com.api.enums.JobEnum;
import br.com.api.enums.TypeOfSalaryEnum;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "employee")
@Getter
@Setter
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_id")
	private Integer identifier;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
	@JoinColumn(name = "individual_id", nullable = false)
	private Individual individual;

	@Column(name = "employee_medicin_continuous", nullable = false)
	private Boolean medicinContinuous;

	@Column(name = "employee_medicin_notes")
	private String medicinNotes;

	@Column(name = "employee_allergies_continuous", nullable = false)
	private Boolean allergiesContinuous;

	@Column(name = "employee_allergies_notes")
	private String allergiesNotes;

	@Column(name = "employee_email", nullable = false, unique = true)
	private String email;

	@Column(name = "employee_phone1", nullable = false)
	private String phone1;

	@Column(name = "employee_phone2")
	private String phone2;

	@Column(name = "employee_instagram")
	private String instagram;

	@Column(name = "employee_facebook")
	private String facebook;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "employee_job", nullable = false)
	private JobEnum job;

	@Enumerated(EnumType.STRING)
	@Column(name = "employee_type_of_salary", nullable = false)
	private TypeOfSalaryEnum typeOfSalary;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "employee_status", nullable = false)
	private EmployeeStatusEnum status;

	@Column(name = "employee_salary_value")
	private Double salaryValue;

	@Column(name = "employee_password")
	private String password;
}
