package br.com.api.flow.grate.item;

import static br.com.api.exceptions.FindByFilterExceptionEnum.MORE_THAN_ONE_REGISTER_FOUND;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.GrateMapper;
import br.com.api.entity.Grate;
import br.com.api.entity.repository.GrateFilter;
import br.com.api.entity.repository.GrateRepositoryImpl;
import br.com.api.exceptions.FindByFilterException;

@Component
public class FindGrateByFilterFlowItem {

	@Autowired
	private GrateRepositoryImpl stateRepositoryImpl;

	@Autowired
	private GrateMapper stateMapper;

	public GrateFilter findByFilter(GrateFilter filter)
			throws FindByFilterException, NoSuchMethodException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, InstantiationException {

		if (Boolean.TRUE.equals(filter.getPageable())) {
			Page<Grate> entities = stateRepositoryImpl.findByFilter(filter,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));

			filter.setResult(entities.map(entity -> stateMapper.toDTO(entity)).toList());
			filter.setTotal(Math.toIntExact(entities.getTotalElements()));
			filter.setTotalPages(Math.toIntExact(entities.getTotalPages()));
			filter.setLast(entities.isLast());
		} else {
			List<Grate> entities = stateRepositoryImpl.findByFilter(filter);
			filter.setResult(entities.stream().map(entity -> stateMapper.toDTO(entity)).toList());
		}

		if (Boolean.TRUE.equals(filter.getResultUnique()) && filter.getResult().size() > 1) {
			throw new FindByFilterException(MORE_THAN_ONE_REGISTER_FOUND);
		}

		return filter;
	}
}
