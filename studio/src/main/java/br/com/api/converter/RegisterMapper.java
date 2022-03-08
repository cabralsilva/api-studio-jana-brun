package br.com.api.converter;

import org.mapstruct.Mapper;

import br.com.api.dto.RegisterDTO;
import br.com.api.entity.Register;

@Mapper(componentModel = "spring")
public interface RegisterMapper {

	public Register toEntity(RegisterDTO dto);

	public RegisterDTO toDTO(Register entity);
}
