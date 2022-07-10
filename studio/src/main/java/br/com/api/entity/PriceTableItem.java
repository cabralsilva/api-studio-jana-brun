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
@Table(name = "price_table_item")
@Getter
@Setter
public class PriceTableItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "price_table_item_id")
	private Integer identifier;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	@ManyToMany
	@JoinTable(
		name = "price_table_item_x_grate_item", 
		joinColumns = { @JoinColumn(name = "price_table_item_id") }, 
		inverseJoinColumns = { @JoinColumn(name = "grate_item_id") })
	private List<GrateItem> grateItemList;
	
	@Column(name = "price_table_item_price")
	private Double price;
}
