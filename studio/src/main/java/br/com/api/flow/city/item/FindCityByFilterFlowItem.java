package br.com.api.flow.city.item;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.CityMapper;
import br.com.api.entity.City;
import br.com.api.entity.repository.CityFilter;
import br.com.api.entity.repository.CityRepository;
import br.com.api.exceptions.FindByFilterException;
import lombok.var;

@Component
public class FindCityByFilterFlowItem {

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private CityMapper cityMapper;

	public CityFilter findByFilter(CityFilter filter)
			throws FindByFilterException, NoSuchMethodException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, InstantiationException {

		Example<City> example = Example.of(cityMapper.toEntity(filter.getExample()));
		if (Boolean.TRUE.equals(filter.getPageable())) {
			final var ret = cityRepository.findAll(example,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));
			filter.setResult(ret.stream().map(p -> cityMapper.toDTO(p)).collect(Collectors.toList()));
			filter.setTotal((int) ret.getTotalElements());
			filter.setTotalPages(ret.getTotalPages());
			return filter;
		}
		final var ret = cityRepository.findAll(example);
		filter.setResult(ret.stream().map(p -> cityMapper.toDTO(p)).collect(Collectors.toList()));
		return filter;
	}
}
