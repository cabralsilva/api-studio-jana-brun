package br.com.api.entity;

import java.time.LocalDate;

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

import br.com.api.enums.GenreEnum;
import br.com.api.enums.TypeOfPersonEnum;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "person")
@Getter
@Setter
public class Person {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "person_id")
	private Integer identifier;

	@Column(name = "person_name", nullable = false)
	private String name;

	@Column(name = "person_nickname")
	private String nickName;

	@Column(name = "person_document_number_1", nullable = false, unique = true)
	private String documentNumber1;

	@Column(name = "person_document_number_2")
	private String documentNumber2;

	@Column(name = "person_born_date")
	private LocalDate bornDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "person_genre")
	private GenreEnum genre;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "person_type", nullable = false)
	private TypeOfPersonEnum type;
	
	@ManyToOne
	@JoinColumn(name = "address_id")
	private Address address;

}
