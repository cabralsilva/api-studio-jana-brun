package br.com.api.converter;

import org.mapstruct.Mapper;

import br.com.api.dto.StudentDTO;
import br.com.api.entity.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {

	public Student toEntity(StudentDTO dto);

	public StudentDTO toDTO(Student entity);
}
