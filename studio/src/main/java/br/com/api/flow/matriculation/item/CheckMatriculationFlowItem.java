package br.com.api.flow.matriculation.item;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.dto.MatriculationDTO;
import br.com.api.entity.repository.MatriculationFilter;
import br.com.api.exceptions.FindByFilterException;
import lombok.var;

@Service
public class CheckMatriculationFlowItem {

	@Autowired
	private FindMatriculationByFilterFlowItem findMatriculationByFilterFlowItem;

	public MatriculationDTO checkIfExist(MatriculationDTO countryDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		final var filter = new MatriculationFilter();
		filter.setExample(countryDTO);
		filter.setPageable(Boolean.FALSE);

		final var existing = findMatriculationByFilterFlowItem.findByFilter(filter).getResult().stream().findFirst();

		if (existing.isPresent()) {
			countryDTO.setIdentifier(existing.get().getIdentifier());
		}
		return countryDTO;
	}
}
