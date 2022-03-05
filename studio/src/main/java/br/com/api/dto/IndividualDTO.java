package br.com.api.dto;

import java.time.LocalDate;

import br.com.api.enums.GenreEnum;
import br.com.api.enums.TypeOfEntityEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class IndividualDTO {
	
	private Integer identifier;
	private String name;
	private String nickName;
	private String documentNumber1;
	private String documentNumber2;
	private LocalDate bornDate;
	private GenreEnum genre;
	private TypeOfEntityEnum typeOfEntity;
	private AddressDTO address;

}
