package br.com.api.converter;

import org.mapstruct.Mapper;

import br.com.api.dto.GrateDTO;
import br.com.api.entity.Grate;

@Mapper(componentModel = "spring")
public interface GrateMapper {

	public Grate toEntity(GrateDTO dto);

	public GrateDTO toDTO(Grate entity);
}
