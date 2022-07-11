package br.com.api.entity;

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
import javax.persistence.Table;

import br.com.api.enums.StatusActiveEnum;
import br.com.api.enums.TypeOfSupplierEnum;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "supplier")
@Getter
@Setter
public class Supplier {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "supplier_id")
	private Integer identifier;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "person_id")
	private Person person;

	@Column(name = "supplier_email", nullable = false, unique = true)
	private String email;

	@Column(name = "supplier_phone1", nullable = false)
	private String phone1;

	@Column(name = "supplier_phone2")
	private String phone2;

	@Enumerated(EnumType.STRING)
	@Column(name = "supplier_type", nullable = false)
	private TypeOfSupplierEnum type;

	@Enumerated(EnumType.STRING)
	@Column(name = "supplier_status")
	private StatusActiveEnum status;
}
