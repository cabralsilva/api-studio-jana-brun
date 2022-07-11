package br.com.api.flow.city.item;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.dto.CityDTO;
import br.com.api.entity.repository.CityFilter;
import br.com.api.exceptions.FindByFilterException;
import lombok.var;

@Service
public class CheckCityFlowItem {

	@Autowired
	private FindCityByFilterFlowItem findCityByFilterFlowItem;

	public CityDTO checkIfExist(CityDTO cityDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		final var filter = new CityFilter();
		filter.setExample(cityDTO);
		filter.setPageable(Boolean.FALSE);
		
		final var existing = findCityByFilterFlowItem.findByFilter(filter).getResult().stream().findFirst();

		if (existing.isPresent()) {
			cityDTO.setIdentifier(existing.get().getIdentifier());
		}
		return cityDTO;
	}
}
