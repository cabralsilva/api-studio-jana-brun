package br.com.api.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.api.enums.GenreEnum;
import br.com.api.enums.TypeOfPersonEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class PersonDTO {
	
	private Integer identifier;
	private String name;
	private String nickName;
	private String documentNumber1;
	private String documentNumber2;
	private LocalDate bornDate;
	private GenreEnum genre;
	private TypeOfPersonEnum type;
	private AddressDTO address;

}
