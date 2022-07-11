package br.com.api.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "matriculation_item")
@Getter
@Setter
public class MatriculationItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "matriculation_item_id")
	private Integer identifier;
	
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	
	@ManyToMany
    @JoinTable(
        name = "matriculation_item_x_grate_item_rel", 
        joinColumns = { @JoinColumn(name = "matriculation_item_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "grate_item_id") }
    )
    private List<GrateItem> grateItemList;
	
	@Column(name = "matriculation_item_unit_price")
	private Double unitPrice;
	
	@Column(name = "matriculation_item_quantity")
	private Double quantity;
	
	@Column(name = "matriculation_item_total_price")
	private Double totalPrice;
	
	@Column(name = "matriculation_item_addition_percent")
	private Double additionPercent;
	
	@Column(name = "matriculation_item_final_price")
	private Double finalPrice;
}
