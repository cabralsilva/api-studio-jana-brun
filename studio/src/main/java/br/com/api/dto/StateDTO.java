package br.com.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class StateDTO {

	private Integer identifier;
	private String code;
	private String name;
	private String timezone;
	private String abbreviation;
	private CountryDTO country;
}
