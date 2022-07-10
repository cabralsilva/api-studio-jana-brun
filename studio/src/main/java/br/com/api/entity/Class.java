package br.com.api.entity;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "class")
@Getter
@Setter
public class Class {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "class_id")
	private Integer identifier;

	@Column(name = "class_code")
	private String code;
	
	@Column(name = "class_description")
	private String description;
	
	@Column(name = "class_invite_whatsapp_group")
	private String inviteWhatsAppGroup;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "class_id", nullable = false)
	private List<RolePayment> rolePaymentList;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="class_id")
	private List<ScheduleDetailClass> scheduleDetailClassList;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "class_student", joinColumns = { @JoinColumn(name = "class_id") }, inverseJoinColumns = {
			@JoinColumn(name = "student_id") })
	private Set<Student> studentList;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "class_creation_date", updatable = false)
	private Calendar creationDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "class_start_date")
	private Calendar startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "class_end_date")
	private Calendar endDate;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

}
