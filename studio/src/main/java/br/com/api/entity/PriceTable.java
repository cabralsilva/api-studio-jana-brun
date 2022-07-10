package br.com.api.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "price_table")
@Getter
@Setter
public class PriceTable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "price_table_id")
	private Integer identifier;

	@Column(name = "price_table_code")
	private String code;
	
	@Column(name = "price_table_description")
	private String description;
	
	@CreationTimestamp
	@Column(name = "price_table_creation_date_time", nullable = false, updatable = false)
	private LocalDateTime creationDateTime;
	
	@Column(name = "price_table_init_date_time", nullable = false)
	private LocalDateTime initDateTime;
	
	@Column(name = "price_table_end_date_time", nullable = false)
	private LocalDateTime endDateTime;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "price_table_id", nullable = false)
	private List<PriceTableItem> itemList;
}
