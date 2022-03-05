package br.com.api.converter;

import org.mapstruct.Mapper;

import br.com.api.dto.CityDTO;
import br.com.api.entity.City;

@Mapper(componentModel = "spring")
public interface CityMapper {

	public City toEntity(CityDTO dto);

	public CityDTO toDTO(City entity);
}
