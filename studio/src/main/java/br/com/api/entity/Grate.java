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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.api.enums.TypeOfValueEnum;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "grate")
@Getter
@Setter
public class Grate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "grate_id")
	private Integer identifier;

	@Column(name = "grate_name", nullable = false)
	private String name;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "grate_type_of_value", nullable = false)
	private TypeOfValueEnum typeOfValue;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "grate_id")
	private List<GrateItem> itemList;
}
