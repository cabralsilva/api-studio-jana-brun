package br.com.api.flow.individual.item;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.dto.IndividualDTO;
import br.com.api.entity.repository.IndividualFilter;
import br.com.api.exceptions.FindByFilterException;

@Service
public class CheckIndividualFlowItem {

	@Autowired
	private FindIndividualByFilterFlowItem findIndividualByFilterFlowItem;

	public IndividualDTO checkIfExist(IndividualDTO individualDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		final var filter = new IndividualFilter();
		filter.setExample(IndividualDTO.builder().documentNumber1(individualDTO.getDocumentNumber1()).build());
		filter.setPageable(Boolean.FALSE);

		final var existing = findIndividualByFilterFlowItem.findByFilter(filter).getResult().stream().findFirst();

		if (existing.isPresent()) {
			individualDTO.setIdentifier(existing.get().getIdentifier());
		}
		return individualDTO;
	}
}
