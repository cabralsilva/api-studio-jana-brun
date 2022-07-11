package br.com.api.flow.classes.item;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.ClassMapper;
import br.com.api.entity.Class;
import br.com.api.entity.repository.ClassFilter;
import br.com.api.entity.repository.ClassRepository;
import br.com.api.entity.repository.ClassRepositoryImpl;
import br.com.api.entity.repository.ClassFilter;
import lombok.var;

@Component
public class SearchClassByFilterFlowItem {

	@Autowired
	private ClassRepositoryImpl classRepositoryImpl;

	@Autowired
	private ClassRepository classRepository;

	@Autowired
	private ClassMapper classMapper;

	public ClassFilter search(ClassFilter filter) throws NoSuchMethodException, SecurityException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {

		if (Boolean.TRUE.equals(filter.getCustomSearch())) {
			return searchCustom(filter);
		}

		Example<Class> example = Example.of(classMapper.toEntity(filter.getExample()));
		if (Boolean.TRUE.equals(filter.getPageable())) {
			final var ret = classRepository.findAll(example,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));
			filter.setResult(ret.stream().map(p -> classMapper.toDTO(p)).collect(Collectors.toList()));
			filter.setTotal((int) ret.getTotalElements());
			filter.setTotalPages(ret.getTotalPages());
			return filter;
		}
		final var ret = classRepository.findAll(example);
		filter.setResult(ret.stream().map(p -> classMapper.toDTO(p)).collect(Collectors.toList()));
		return filter;
	}

	public ClassFilter searchCustom(ClassFilter filter) throws NoSuchMethodException, SecurityException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {

		if (Boolean.TRUE.equals(filter.getPageable())) {
			final var ret = classRepositoryImpl.findByFilter(filter,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));
			filter.setResult(ret.stream().map(p -> classMapper.toDTO(p)).collect(Collectors.toList()));
			filter.setTotal((int) ret.getTotalElements());
			filter.setTotalPages(ret.getTotalPages());
			return filter;
		}
		final var ret = classRepositoryImpl.findByFilter(filter);
		filter.setResult(ret.stream().map(p -> classMapper.toDTO(p)).collect(Collectors.toList()));
		return filter;
	}
}
