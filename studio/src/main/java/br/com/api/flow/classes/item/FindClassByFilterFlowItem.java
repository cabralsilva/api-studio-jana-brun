package br.com.api.flow.classes.item;

import static br.com.api.exceptions.FindByFilterExceptionEnum.MORE_THAN_ONE_REGISTER_FOUND;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.ClassMapper;
import br.com.api.entity.Class;
import br.com.api.entity.repository.ClassFilter;
import br.com.api.entity.repository.ClassRepositoryImpl;
import br.com.api.exceptions.FindByFilterException;

@Component
public class FindClassByFilterFlowItem {

	@Autowired
	private ClassRepositoryImpl stateRepositoryImpl;

	@Autowired
	private ClassMapper stateMapper;

	public ClassFilter findByFilter(ClassFilter filter)
			throws FindByFilterException, NoSuchMethodException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, InstantiationException {

		if (Boolean.TRUE.equals(filter.getPageable())) {
			Page<Class> entities = stateRepositoryImpl.findByFilter(filter,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));

			filter.setResult(entities.map(entity -> stateMapper.toDTO(entity)).toList());
			filter.setTotal(Math.toIntExact(entities.getTotalElements()));
			filter.setTotalPages(Math.toIntExact(entities.getTotalPages()));
			filter.setLast(entities.isLast());
		} else {
			List<Class> entities = stateRepositoryImpl.findByFilter(filter);
			filter.setResult(entities.stream().map(entity -> stateMapper.toDTO(entity)).toList());
		}

		if (Boolean.TRUE.equals(filter.getResultUnique()) && filter.getResult().size() > 1) {
			throw new FindByFilterException(MORE_THAN_ONE_REGISTER_FOUND);
		}

		return filter;
	}
}
