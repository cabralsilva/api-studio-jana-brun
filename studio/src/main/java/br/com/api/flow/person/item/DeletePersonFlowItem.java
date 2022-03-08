package br.com.api.flow.person.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.entity.repository.PersonRepository;

@Component
public class DeletePersonFlowItem {

	@Autowired
	private PersonRepository personRepository;

	public void delete(Integer identifier) {

		personRepository.deleteById(identifier);
	}
}
