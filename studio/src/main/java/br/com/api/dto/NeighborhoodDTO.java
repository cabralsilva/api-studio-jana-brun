package br.com.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class NeighborhoodDTO {

	private Integer identifier;
	private String name;
	private CityDTO city;

}
