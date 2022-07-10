package br.com.api.flow.classes.item;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.ClassMapper;
import br.com.api.dto.ClassDTO;
import br.com.api.entity.Class;
import br.com.api.entity.repository.ClassRepository;

@Component
public class SearchClassByEndDateGreatherThanFlowItem {

	@Autowired
	private ClassRepository classRepository;

	@Autowired
	private ClassMapper classMapper;

	public List<ClassDTO> search(Calendar target) {
		List<Class> ret = classRepository.findByEndDateGreaterThan(target);

		return ret.stream().map(p -> classMapper.toDTO(p)).collect(Collectors.toList());
	}
}
