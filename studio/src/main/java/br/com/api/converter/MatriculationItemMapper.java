package br.com.api.converter;

import org.mapstruct.Mapper;

import br.com.api.dto.MatriculationItemDTO;
import br.com.api.entity.MatriculationItem;

@Mapper
public interface MatriculationItemMapper {

	public MatriculationItem toEntity(MatriculationItemDTO dto);

	public MatriculationItemDTO toDTO(MatriculationItem entity);

}
