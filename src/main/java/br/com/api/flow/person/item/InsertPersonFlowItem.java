package br.com.api.flow.person.item;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.com.api.converter.PersonMapper;
import br.com.api.dto.PersonDTO;
import br.com.api.entity.repository.PersonRepository;

@Component
public class InsertPersonFlowItem {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private UpdatePersonFlowItem updatePersonFlowItem;

	@Autowired
	private PersonMapper personMapper;

	public PersonDTO insert(@NonNull PersonDTO person) {

		if (Objects.nonNull(person.getIdentifier())) {
			return updatePersonFlowItem.update(person);
		}

		return personMapper.toDTO(personRepository.saveAndFlush(personMapper.toEntity(person)));
	}
}
