package br.com.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "grate_item")
@Getter
@Setter
public class GrateItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "grate_item_id")
	private Integer identifier;

	@Column(name = "grate_item_value", nullable = false)
	private String value;
	
	@ManyToOne
	@JoinColumn(name = "grate_id")
    @JsonBackReference
	private Grate grate;
}