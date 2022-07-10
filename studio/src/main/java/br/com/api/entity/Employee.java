package br.com.api.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.api.enums.EmployeeStatusEnum;
import br.com.api.enums.JobEnum;
import br.com.api.enums.RoleEnum;
import br.com.api.enums.TypeOfPaymentEnum;
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

	@ManyToOne
	@JoinColumn(name = "person_id")
	private Person person;

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
	private TypeOfPaymentEnum typeOfSalary;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "employee_status", nullable = false)
	private EmployeeStatusEnum status;

	@Column(name = "employee_salary_value")
	private Double salaryValue;

	@Column(name = "employee_password")
	private String password;
	
	@Column(name = "employee_creation_date")
	private LocalDate creationDate;
	
	@Column(name = "employee_admission_date")
	private LocalDate admissionDate;

	@Column(name = "employee_demission_date")
	private LocalDate demissionDate;
	
	@ElementCollection(targetClass = RoleEnum.class)
	@JoinTable(name = "employee_role", joinColumns = @JoinColumn(name = "employee_id"))
	@Column(name = "role_id")
	@Enumerated(EnumType.STRING)
	private List<RoleEnum> roleList;
}
