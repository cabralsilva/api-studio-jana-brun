package br.com.api.flow.person.item;

import static br.com.api.exceptions.FindByFilterExceptionEnum.MORE_THAN_ONE_REGISTER_FOUND;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.PersonMapper;
import br.com.api.entity.Person;
import br.com.api.entity.repository.PersonFilter;
import br.com.api.entity.repository.PersonRepositoryImpl;
import br.com.api.exceptions.FindByFilterException;

@Component
public class FindPersonByFilterFlowItem {

	@Autowired
	private PersonRepositoryImpl personRepositoryImpl;

	@Autowired
	private PersonMapper personMapper;

	public PersonFilter findByFilter(PersonFilter filter)
			throws FindByFilterException, NoSuchMethodException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, InstantiationException {

		if (Boolean.TRUE.equals(filter.getPageable())) {
			Page<Person> entities = personRepositoryImpl.findByFilter(filter,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));

			filter.setResult(entities.map(entity -> personMapper.toDTO(entity)).toList());
			filter.setTotal(Math.toIntExact(entities.getTotalElements()));
			filter.setTotalPages(Math.toIntExact(entities.getTotalPages()));
			filter.setLast(entities.isLast());
		} else {
			List<Person> entities = personRepositoryImpl.findByFilter(filter);
			filter.setResult(entities.stream().map(entity -> personMapper.toDTO(entity)).toList());			
		}

		if (Boolean.TRUE.equals(filter.getResultUnique()) && filter.getResult().size() > 1) {
			throw new FindByFilterException(MORE_THAN_ONE_REGISTER_FOUND);
		}

		return filter;
	}
}
