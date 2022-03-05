package br.com.api.flow.individual.item;

import static br.com.api.exceptions.FindByFilterExceptionEnum.MORE_THAN_ONE_REGISTER_FOUND;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.IndividualMapper;
import br.com.api.entity.Individual;
import br.com.api.entity.repository.IndividualFilter;
import br.com.api.entity.repository.IndividualRepositoryImpl;
import br.com.api.exceptions.FindByFilterException;

@Component
public class FindIndividualByFilterFlowItem {

	@Autowired
	private IndividualRepositoryImpl individualRepositoryImpl;

	@Autowired
	private IndividualMapper individualMapper;

	public IndividualFilter findByFilter(IndividualFilter filter)
			throws FindByFilterException, NoSuchMethodException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, InstantiationException {

		if (Boolean.TRUE.equals(filter.getPageable())) {
			Page<Individual> entities = individualRepositoryImpl.findByFilter(filter,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));

			filter.setResult(entities.map(entity -> individualMapper.toDTO(entity)).toList());
			filter.setTotal(Math.toIntExact(entities.getTotalElements()));
			filter.setTotalPages(Math.toIntExact(entities.getTotalPages()));
			filter.setLast(entities.isLast());
		} else {
			List<Individual> entities = individualRepositoryImpl.findByFilter(filter);
			filter.setResult(entities.stream().map(entity -> individualMapper.toDTO(entity)).toList());			
		}

		if (Boolean.TRUE.equals(filter.getResultUnique()) && filter.getResult().size() > 1) {
			throw new FindByFilterException(MORE_THAN_ONE_REGISTER_FOUND);
		}

		return filter;
	}
}
