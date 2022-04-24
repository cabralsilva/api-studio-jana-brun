package br.com.api.entity;

import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.Table;

import br.com.api.enums.StatusActiveEnum;
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

	@Column(name = "product_code")
	private String code;

	@Column(name = "product_description", nullable = false)
	private String description;

	@Column(name = "product_unit_price", nullable = false)
	private Double unitPrice;

	@Enumerated(EnumType.STRING)
	@Column(name = "product_status")
	private StatusActiveEnum status;

	@ManyToMany
	@JoinTable(name = "product_x_grate_rel", 
		joinColumns = { @JoinColumn(name = "product_id") }, 
		inverseJoinColumns = { @JoinColumn(name = "grate_id") })
	private List<Grate> grateList;

}
