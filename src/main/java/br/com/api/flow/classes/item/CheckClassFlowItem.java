package br.com.api.flow.classes.item;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.dto.ClassDTO;
import br.com.api.entity.repository.ClassFilter;
import br.com.api.exceptions.FindByFilterException;
import lombok.var;

@Service
public class CheckClassFlowItem {

	@Autowired
	private SearchClassByFilterFlowItem findClassByFilterFlowItem;

	public ClassDTO checkIfExist(ClassDTO cityDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		final var filter = new ClassFilter();
		filter.setExample(cityDTO);
		filter.setPageable(Boolean.FALSE);

		final var existing = findClassByFilterFlowItem.search(filter).getResult().stream().findFirst();

		if (existing.isPresent()) {
			cityDTO.setIdentifier(existing.get().getIdentifier());
		}
		return cityDTO;
	}
}
