package br.com.api.flow.country.item;

import static br.com.api.exceptions.FindByFilterExceptionEnum.MORE_THAN_ONE_matriculation_FOUND;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.CountryMapper;
import br.com.api.entity.Country;
import br.com.api.entity.repository.CountryFilter;
import br.com.api.entity.repository.CountryRepositoryImpl;
import br.com.api.exceptions.FindByFilterException;

@Component
public class FindCountryByFilterFlowItem {

	@Autowired
	private CountryRepositoryImpl countryRepositoryImpl;

	@Autowired
	private CountryMapper countryMapper;

	public CountryFilter findByFilter(CountryFilter filter)
			throws FindByFilterException, NoSuchMethodException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, InstantiationException {

		if (Boolean.TRUE.equals(filter.getPageable())) {
			Page<Country> entities = countryRepositoryImpl.findByFilter(filter,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));

			filter.setResult(entities.map(entity -> countryMapper.toDTO(entity)).toList());
			filter.setTotal(Math.toIntExact(entities.getTotalElements()));
			filter.setTotalPages(Math.toIntExact(entities.getTotalPages()));
			filter.setLast(entities.isLast());
		} else {
			List<Country> entities = countryRepositoryImpl.findByFilter(filter);
			filter.setResult(entities.stream().map(entity -> countryMapper.toDTO(entity)).collect(Collectors.toList()));
		}

		if (Boolean.TRUE.equals(filter.getResultUnique()) && filter.getResult().size() > 1) {
			throw new FindByFilterException(MORE_THAN_ONE_matriculation_FOUND);
		}

		return filter;
	}
}
