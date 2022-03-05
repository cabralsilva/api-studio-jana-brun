package br.com.api.flow.neighborhood.item;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.dto.NeighborhoodDTO;
import br.com.api.entity.repository.NeighborhoodFilter;
import br.com.api.exceptions.FindByFilterException;

@Service
public class CheckNeighborhoodFlowItem {

	@Autowired
	private FindNeighborhoodByFilterFlowItem findNeighborhoodByFilterFlowItem;

	public NeighborhoodDTO checkIfExist(NeighborhoodDTO neighborhoodDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		final var filter = new NeighborhoodFilter();
		filter.setExample(neighborhoodDTO);
		filter.setPageable(Boolean.FALSE);
		
		final var existing = findNeighborhoodByFilterFlowItem.findByFilter(filter).getResult().stream().findFirst();

		if (existing.isPresent()) {
			neighborhoodDTO.setIdentifier(existing.get().getIdentifier());
		}
		return neighborhoodDTO;
	}
}
