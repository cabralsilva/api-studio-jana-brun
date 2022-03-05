package br.com.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "country")
@Getter
@Setter
public class Country {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "country_id")
	private Integer identifier;

	@Column(name = "country_code", unique = true)
	private String code;

	@Column(name = "country_name", nullable = false)
	private String name;

	@Column(name = "country_ddi", nullable = false)
	private Integer ddi;

	@Column(name = "country_language", nullable = false)
	private String language;

	@Column(name = "country_abbreviation", nullable = false)
	private String abbreviation;
}
