package br.com.api.flow.country.item;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.dto.CountryDTO;
import br.com.api.entity.repository.CountryFilter;
import br.com.api.exceptions.FindByFilterException;

@Service
public class CheckCountryFlowItem {

	@Autowired
	private FindCountryByFilterFlowItem findCountryByFilterFlowItem;

	public CountryDTO checkIfExist(CountryDTO countryDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		final var filter = new CountryFilter();
		filter.setExample(countryDTO);
		filter.setPageable(Boolean.FALSE);
		
		final var existing = findCountryByFilterFlowItem.findByFilter(filter).getResult().stream().findFirst();

		if (existing.isPresent()) {
			countryDTO.setIdentifier(existing.get().getIdentifier());
		}
		return countryDTO;
	}
}
