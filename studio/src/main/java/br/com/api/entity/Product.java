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
@Table(name = "product")
@Getter
@Setter
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Integer identifier;

	@Column(name = "product_name", nullable = false)
	private String name;
	
	@Column(name = "product_description", nullable = false)
	private String description;
	
	@Column(name = "product_unit_price", nullable = false)
	private Double unitPrice;
	
	@Column(name = "product_active", nullable = false)
	private Boolean active;

}
