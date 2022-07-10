package br.com.api.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

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
public class PersonDTO implements Serializable {

	private static final long serialVersionUID = -1525776028205338929L;
	private Integer identifier;
	private String name;
	private String nickName;
	private String documentNumber1;
	private String documentNumber2;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate bornDate;
	private GenreEnum genre;
	private TypeOfPersonEnum type;
	private AddressDTO address;

}
