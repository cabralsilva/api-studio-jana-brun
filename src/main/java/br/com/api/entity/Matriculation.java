package br.com.api.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import br.com.api.enums.MatriculationStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "matriculation")
@Getter
@Setter
public class Matriculation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "matriculation_id")
	private Integer identifier;

	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;

	@ManyToOne
	@JoinColumn(name = "responsible_financial_id")
	private Person responsibleFinancial;

	@CreationTimestamp
	@Column(name = "matriculation_creation_date_time", nullable = false)
	private LocalDateTime creationDateTime;

	@Column(name = "matriculation_effective_date_time")
	private LocalDateTime effectiveDateTime;
	
	@Column(name = "matriculation_day_of_month_to_payment")
	private Integer dayOfMonthToPayment;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "matriculation_id", nullable = false)
    private List<MatriculationItem> matriculationItemList;
	
	@ManyToMany
	@JoinTable(name = "matriculation_x_class_rel", 
		joinColumns = { @JoinColumn(name = "matriculation_id") }, 
		inverseJoinColumns = { @JoinColumn(name = "class_id") })
    private List<Class> classList;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "matriculation_status", nullable = false)
	private MatriculationStatusEnum status;

	@Column(name = "matriculation_observation")
	private String observation;

}
