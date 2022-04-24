package br.com.api.flow.grate.item;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.dto.GrateDTO;
import br.com.api.entity.repository.GrateFilter;
import br.com.api.exceptions.FindByFilterException;
import lombok.var;

@Service
public class CheckGrateFlowItem {

	@Autowired
	private FindGrateByFilterFlowItem findGrateByFilterFlowItem;

	public GrateDTO checkIfExist(GrateDTO grateDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		final var filter = new GrateFilter();
		filter.setExample(grateDTO);
		filter.setPageable(Boolean.FALSE);

		final var existing = findGrateByFilterFlowItem.findByFilter(filter).getResult().stream().findFirst();

		if (existing.isPresent()) {
			grateDTO.setIdentifier(existing.get().getIdentifier());
		}
		return grateDTO;
	}
}
