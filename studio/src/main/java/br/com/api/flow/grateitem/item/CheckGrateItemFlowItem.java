package br.com.api.flow.grateitem.item;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.dto.GrateItemDTO;
import br.com.api.entity.repository.GrateItemFilter;
import br.com.api.exceptions.FindByFilterException;
import lombok.var;

@Service
public class CheckGrateItemFlowItem {

	@Autowired
	private FindGrateItemByFilterFlowItem findGrateItemByFilterFlowItem;

	public GrateItemDTO checkIfExist(GrateItemDTO grateDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		final var filter = new GrateItemFilter();
		filter.setExample(grateDTO);
		filter.setPageable(Boolean.FALSE);

		final var existing = findGrateItemByFilterFlowItem.findByFilter(filter).getResult().stream().findFirst();

		if (existing.isPresent()) {
			grateDTO.setIdentifier(existing.get().getIdentifier());
		}
		return grateDTO;
	}
}
