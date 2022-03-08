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

import br.com.api.enums.SchoolLevelEnum;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "student")
@Getter
@Setter
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "student_id")
	private Integer identifier;

	@ManyToOne
	@JoinColumn(name = "responsible_id", nullable = false)
	private Person responsible;
	
	@ManyToOne
	@JoinColumn(name = "person_id", nullable = false)
	private Person person;

	@ManyToOne
	@JoinColumn(name = "school_id", nullable = false)
	private Person school;

	@Enumerated(EnumType.STRING)
	@Column(name = "student_school_level", nullable = false)
	private SchoolLevelEnum schoolLevel;

	@Column(name = "student_medicin_continuous", nullable = false)
	private Boolean medicinContinuous;

	@Column(name = "student_medicin_notes")
	private String medicinNotes;

	@Column(name = "student_allergies_continuous", nullable = false)
	private Boolean allergiesContinuous;

	@Column(name = "student_allergies_notes")
	private String allergiesNotes;

	@Column(name = "student_responsible_email", nullable = false)
	private String responsibleEmail;

	@Column(name = "student_responsible_phone1", nullable = false)
	private String responsiblePhone1;

	@Column(name = "student_responsible_phone2")
	private String responsiblePhone2;

	@Column(name = "student_responsible_instagram")
	private String responsibleInstagram;

	@Column(name = "student_responsible_facebook")
	private String responsibleFacebook;

}
