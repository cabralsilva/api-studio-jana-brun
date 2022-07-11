package br.com.api.entity;

import javax.persistence.CascadeType;
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
@Table(name = "address")
@Getter
@Setter
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "address_id")
	private Integer identifier;

	@Column(name = "address_street", nullable = false)
	private String street;
	
	@Column(name = "address_number", nullable = false)
	private String number;

	@Column(name = "address_complement")
	private String complement;

	@Column(name = "address_zipcode")
	private String zipCode;
	
	@ManyToOne
	@JoinColumn(name = "neighborhood_id", nullable = false)
	private Neighborhood neighborhood;

}
