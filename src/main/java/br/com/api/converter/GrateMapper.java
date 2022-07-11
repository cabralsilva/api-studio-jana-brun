package br.com.api.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.api.dto.GrateDTO;
import br.com.api.entity.Grate;

@Mapper(componentModel = "spring")
public interface GrateMapper {

	@Mapping(target = "itemList", ignore = true)
	public Grate toEntity(GrateDTO dto);

	public GrateDTO toDTO(Grate entity);
}
