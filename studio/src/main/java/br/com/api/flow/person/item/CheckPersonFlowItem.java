package br.com.api.flow.person.item;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.dto.PersonDTO;
import br.com.api.entity.repository.PersonFilter;
import br.com.api.exceptions.FindByFilterException;

@Service
public class CheckPersonFlowItem {

	@Autowired
	private FindPersonByFilterFlowItem findPersonByFilterFlowItem;

	public PersonDTO checkIfExist(PersonDTO individualDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		final var filter = new PersonFilter();
		filter.setExample(PersonDTO.builder().documentNumber1(individualDTO.getDocumentNumber1()).build());
		filter.setPageable(Boolean.FALSE);

		final var existing = findPersonByFilterFlowItem.findByFilter(filter).getResult().stream().findFirst();

		if (existing.isPresent()) {
			individualDTO.setIdentifier(existing.get().getIdentifier());
		}
		return individualDTO;
	}
}
