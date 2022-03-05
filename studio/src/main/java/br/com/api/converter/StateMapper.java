package br.com.api.converter;

import org.mapstruct.Mapper;

import br.com.api.dto.StateDTO;
import br.com.api.entity.State;

@Mapper(componentModel = "spring")
public interface StateMapper {

	public State toEntity(StateDTO dto);

	public StateDTO toDTO(State entity);
}
