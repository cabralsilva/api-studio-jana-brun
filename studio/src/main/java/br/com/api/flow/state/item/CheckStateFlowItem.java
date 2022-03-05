package br.com.api.flow.state.item;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.dto.StateDTO;
import br.com.api.entity.repository.StateFilter;
import br.com.api.exceptions.FindByFilterException;

@Service
public class CheckStateFlowItem {

	@Autowired
	private FindStateByFilterFlowItem findStateByFilterFlowItem;

	public StateDTO checkIfExist(StateDTO stateDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		final var filter = new StateFilter();
		filter.setExample(stateDTO);
		filter.setPageable(Boolean.FALSE);
		
		final var existing = findStateByFilterFlowItem.findByFilter(filter).getResult().stream().findFirst();

		if (existing.isPresent()) {
			stateDTO.setIdentifier(existing.get().getIdentifier());
		}
		return stateDTO;
	}
}
