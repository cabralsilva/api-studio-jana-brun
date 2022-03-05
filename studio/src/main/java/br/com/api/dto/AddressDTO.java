package br.com.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class AddressDTO {

	private Integer identifier;
	private String street;
	private String number;
	private String complement;
	private String zipCode;
	private NeighborhoodDTO neighborhood;

}
