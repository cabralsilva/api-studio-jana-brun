package br.com.api.entity;

import java.time.LocalDateTime;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import br.com.api.enums.OrderStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "`order`")
@Getter
@Setter
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Integer identifier;

	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Person customer;

	@CreationTimestamp
	@Column(name = "order_creation_date_time", nullable = false)
	private LocalDateTime creationDateTime;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "order_id", nullable = false)
    private List<OrderItem> orderItemList;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "order_status", nullable = false)
	private OrderStatusEnum status;

	@Column(name = "order_observation")
	private String observation;

}
