package br.com.api.entity;

import java.time.LocalDate;

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

import br.com.api.enums.GenreEnum;
import br.com.api.enums.TypeOfEntityEnum;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "individual")
@Getter
@Setter
public class Individual {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "individual_id")
	private Integer identifier;

	@Column(name = "individual_name", nullable = false)
	private String name;

	@Column(name = "individual_nickname")
	private String nickName;

	@Column(name = "individual_document_number_1", nullable = false, unique = true)
	private String documentNumber1;

	@Column(name = "individual_document_number_2")
	private String documentNumber2;

	@Column(name = "individual_born_date", nullable = false)
	private LocalDate bornDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "individual_genre", nullable = false)
	private GenreEnum genre;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "individual_type_of_entity", nullable = false)
	private TypeOfEntityEnum typeOfEntity;
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH})
	@JoinColumn(name = "address_id")
	private Address address;

}
