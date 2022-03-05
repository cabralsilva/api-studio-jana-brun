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
@Table(name = "state")
@Getter
@Setter
public class State {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "state_id")
	private Integer identifier;

	@Column(name = "state_code", nullable = false)
	private String code;

	@Column(name = "state_name", nullable = false)
	private String name;

	@Column(name = "state_timezone", nullable = false)
	private String timezone;

	@Column(name = "state_abbreviation", nullable = false)
	private String abbreviation;

	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH})
	@JoinColumn(name = "country_id", nullable = false)
	private Country country;
}
