package br.com.api.converter;

import org.mapstruct.Mapper;

import br.com.api.dto.ClassroomDTO;
import br.com.api.entity.Classroom;

@Mapper(componentModel = "spring")
public interface ClassroomMapper {

	public Classroom toEntity(ClassroomDTO dto);

	public ClassroomDTO toDTO(Classroom entity);
}
