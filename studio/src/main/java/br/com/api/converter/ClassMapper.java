package br.com.api.converter;

import org.mapstruct.Mapper;

import br.com.api.dto.ClassDTO;
import br.com.api.entity.Class;

@Mapper(componentModel = "spring")
public interface ClassMapper {

	public Class toEntity(ClassDTO dto);

	public ClassDTO toDTO(Class entity);
}
