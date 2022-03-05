package br.com.api.converter;

import org.mapstruct.Mapper;

import br.com.api.dto.IndividualDTO;
import br.com.api.entity.Individual;

@Mapper(componentModel = "spring")
public interface IndividualMapper {

	public Individual toEntity(IndividualDTO dto);

	public IndividualDTO toDTO(Individual entity);
}
