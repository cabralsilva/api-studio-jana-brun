package br.com.api.converter;

import org.mapstruct.Mapper;

import br.com.api.dto.CountryDTO;
import br.com.api.entity.Country;

@Mapper(componentModel = "spring")
public interface CountryMapper {

	public Country toEntity(CountryDTO dto);

	public CountryDTO toDTO(Country entity);
}
