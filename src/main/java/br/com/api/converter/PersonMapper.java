package br.com.api.converter;

import org.mapstruct.Mapper;

import br.com.api.dto.PersonDTO;
import br.com.api.entity.Person;

@Mapper(componentModel = "spring")
public interface PersonMapper {

	public Person toEntity(PersonDTO dto);

	public PersonDTO toDTO(Person entity);
}
