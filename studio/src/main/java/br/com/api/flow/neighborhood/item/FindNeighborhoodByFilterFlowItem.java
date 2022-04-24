package br.com.api.flow.neighborhood.item;

import static br.com.api.exceptions.FindByFilterExceptionEnum.MORE_THAN_ONE_matriculation_FOUND;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.NeighborhoodMapper;
import br.com.api.entity.Neighborhood;
import br.com.api.entity.repository.NeighborhoodFilter;
import br.com.api.entity.repository.NeighborhoodRepositoryImpl;
import br.com.api.exceptions.FindByFilterException;

@Component
public class FindNeighborhoodByFilterFlowItem {

	@Autowired
	private NeighborhoodRepositoryImpl neighborhoodRepositoryImpl;

	@Autowired
	private NeighborhoodMapper neighborhoodMapper;

	public NeighborhoodFilter findByFilter(NeighborhoodFilter filter)
			throws FindByFilterException, NoSuchMethodException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, InstantiationException {

		if (Boolean.TRUE.equals(filter.getPageable())) {
			Page<Neighborhood> entities = neighborhoodRepositoryImpl.findByFilter(filter,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));

			filter.setResult(entities.map(entity -> neighborhoodMapper.toDTO(entity)).toList());
			filter.setTotal(Math.toIntExact(entities.getTotalElements()));
			filter.setTotalPages(Math.toIntExact(entities.getTotalPages()));
			filter.setLast(entities.isLast());
		} else {
			List<Neighborhood> entities = neighborhoodRepositoryImpl.findByFilter(filter);
			filter.setResult(
					entities.stream().map(entity -> neighborhoodMapper.toDTO(entity)).collect(Collectors.toList()));
		}

		if (Boolean.TRUE.equals(filter.getResultUnique()) && filter.getResult().size() > 1) {
			throw new FindByFilterException(MORE_THAN_ONE_matriculation_FOUND);
		}

		return filter;
	}
}
