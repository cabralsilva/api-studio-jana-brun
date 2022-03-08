package br.com.api.entity;

import java.time.LocalDateTime;
import java.util.List;

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
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import br.com.api.enums.RegisterStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "register")
@Getter
@Setter
public class Register {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "register_id")
	private Integer identifier;

	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;

	@ManyToOne
	@JoinColumn(name = "responsible_financial_id")
	private Person responsibleFinancial;

	@CreationTimestamp
	@Column(name = "register_creation_date_time", nullable = false)
	private LocalDateTime creationDateTime;

	@Column(name = "register_effective_date_time")
	private LocalDateTime effectiveDateTime;
	
	@Column(name = "register_day_of_month_to_payment")
	private Integer dayOfMonthToPayment;
	
	@ManyToMany
    @JoinTable(
        name = "register_product_rel", 
        joinColumns = { @JoinColumn(name = "register_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "product_id") }
    )
    private List<Product> productList;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "register_status", nullable = false)
	private RegisterStatusEnum status;

}
