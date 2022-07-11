package br.com.api.converter;

import org.mapstruct.Mapper;

import br.com.api.dto.NeighborhoodDTO;
import br.com.api.entity.Neighborhood;

@Mapper(componentModel = "spring")
public interface NeighborhoodMapper {

	public Neighborhood toEntity(NeighborhoodDTO dto);

	public NeighborhoodDTO toDTO(Neighborhood entity);
}
