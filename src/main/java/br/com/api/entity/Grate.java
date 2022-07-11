package br.com.api.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.api.enums.StatusActiveEnum;
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

	@Column(name = "grate_code")
	private String code;
	
	@Column(name = "grate_description")
	private String description;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "grate_type_of_value", nullable = false)
	private TypeOfValueEnum typeOfValue;

	@Enumerated(EnumType.STRING)
	@Column(name = "grate_status")
	private StatusActiveEnum status;
	
	@OneToMany(mappedBy = "grate")
    @JsonManagedReference
	private List<GrateItem> itemList;
}
