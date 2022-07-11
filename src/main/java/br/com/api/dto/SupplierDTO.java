package br.com.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.api.enums.StatusActiveEnum;
import br.com.api.enums.TypeOfSupplierEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class SupplierDTO {

	private Integer identifier;
	private PersonDTO person;
	private String email;
	private String phone1;
	private String phone2;
	private TypeOfSupplierEnum type;
	private StatusActiveEnum status;
}
