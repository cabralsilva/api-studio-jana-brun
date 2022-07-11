package br.com.api.flow.person.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.PersonMapper;
import br.com.api.dto.PersonDTO;
import br.com.api.entity.repository.PersonRepository;

@Component
public class UpdatePersonFlowItem {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private PersonMapper personMapper;

	public PersonDTO update(PersonDTO person) {

		return personMapper.toDTO(personRepository.save(personMapper.toEntity(person)));
	}
}
