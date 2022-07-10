package br.com.api.flow.person.item;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.PersonMapper;
import br.com.api.entity.Person;
import br.com.api.entity.repository.PersonFilter;
import br.com.api.entity.repository.PersonRepository;
import br.com.api.entity.repository.PersonRepositoryImpl;
import lombok.var;

@Component
public class FindPersonByFilterFlowItem {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private PersonRepositoryImpl personRepositoryImpl;

	@Autowired
	private PersonMapper personMapper;

	public PersonFilter findByFilter(PersonFilter filter) {

		Example<Person> example = Example.of(personMapper.toEntity(filter.getExample()));
		if (Boolean.TRUE.equals(filter.getPageable())) {
			final var ret = personRepository.findAll(example,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));
			filter.setResult(ret.stream().map(p -> personMapper.toDTO(p)).collect(Collectors.toList()));
			filter.setTotal((int) ret.getTotalElements());
			filter.setTotalPages(ret.getTotalPages());
			return filter;
		}
		final var ret = personRepository.findAll(example);
		filter.setResult(ret.stream().map(p -> personMapper.toDTO(p)).collect(Collectors.toList()));
		return filter;
	}

	public PersonFilter findByFilterImpl(PersonFilter filter) throws NoSuchMethodException, SecurityException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {

		if (Boolean.TRUE.equals(filter.getPageable())) {
			final var ret = personRepositoryImpl.findByFilter(filter,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));
			filter.setResult(ret.stream().map(p -> personMapper.toDTO(p)).collect(Collectors.toList()));
			filter.setTotal((int) ret.getTotalElements());
			filter.setTotalPages(ret.getTotalPages());
			return filter;
		}
		final var ret = personRepositoryImpl.findByFilter(filter);
		filter.setResult(ret.stream().map(p -> personMapper.toDTO(p)).collect(Collectors.toList()));
		return filter;
	}
}
