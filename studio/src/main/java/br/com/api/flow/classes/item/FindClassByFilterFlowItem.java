package br.com.api.flow.classes.item;

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
import lombok.var;

@Component
public class FindClassByFilterFlowItem {

	@Autowired
	private ClassRepositoryImpl classRepositoryImpl;

	@Autowired
	private ClassRepository classRepository;

	@Autowired
	private ClassMapper classMapper;

	public ClassFilter findByFilter(ClassFilter filter) {

		Example<Class> example = Example.of(classMapper.toEntity(filter.getExample()));
		final var ret = classRepository.findAll(example, PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));
		filter.setResult(ret.stream().map(p -> classMapper.toDTO(p)).collect(Collectors.toList()));

		return filter;
	}
}
