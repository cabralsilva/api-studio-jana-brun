package br.com.api.flow.matriculationitem.item;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.dto.MatriculationItemDTO;
import br.com.api.entity.repository.MatriculationItemFilter;
import br.com.api.exceptions.FindByFilterException;
import lombok.var;

@Service
public class CheckMatriculationItemFlowItem {

	@Autowired
	private FindMatriculationItemByFilterFlowItem findMatriculationItemByFilterFlowItem;

	public MatriculationItemDTO checkIfExist(MatriculationItemDTO MatriculationItemDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		final var filter = new MatriculationItemFilter();
		filter.setExample(MatriculationItemDTO);
		filter.setPageable(Boolean.FALSE);

		final var existing = findMatriculationItemByFilterFlowItem.findByFilter(filter).getResult().stream()
				.findFirst();

		if (existing.isPresent()) {
			MatriculationItemDTO.setIdentifier(existing.get().getIdentifier());
		}
		return MatriculationItemDTO;
	}
}
