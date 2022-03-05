package br.com.api.flow.classes.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.ClassMapper;
import br.com.api.dto.ClassDTO;
import br.com.api.entity.repository.ClassRepository;

@Component
public class UpdateClassFlowItem {

	@Autowired
	private ClassRepository classRepository;

	@Autowired
	private ClassMapper classMapper;

	public ClassDTO update(ClassDTO clazz) {

		return classMapper.toDTO(classRepository.save(classMapper.toEntity(clazz)));
	}
}
