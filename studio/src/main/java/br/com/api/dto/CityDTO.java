package br.com.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class CityDTO {

	private Integer identifier;
	private String name;
	private String code;
	private String abbreviation;
	private StateDTO state;
}
