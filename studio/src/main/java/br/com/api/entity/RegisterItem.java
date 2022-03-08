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
@Table(name = "register_item")
@Getter
@Setter
public class RegisterItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "register_item_id")
	private Integer identifier;
	
	@ManyToOne
	@JoinColumn(name = "register_id")
	private Register register;
	
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	
	@ManyToMany
    @JoinTable(
        name = "register_item_x_grate_item_rel", 
        joinColumns = { @JoinColumn(name = "register_item_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "grate_item_id") }
    )
    private List<GrateItem> grateItemList;
	
	@Column(name = "register_item_unit_price", nullable = false)
	private Double unitPrice;
	
	@Column(name = "register_item_quantity", nullable = false)
	private Double quantity;
	
	@Column(name = "register_item_total_price", nullable = false)
	private Double totalPrice;
	
	@Column(name = "register_item_addition_percent", nullable = false)
	private Double additionPercent;
	
	@Column(name = "register_item_final_price", nullable = false)
	private Double finalPrice;
}
