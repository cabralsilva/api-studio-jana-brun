package br.com.api.flow.classes.item;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.ClassMapper;
import br.com.api.dto.ClassDTO;
import br.com.api.entity.repository.ClassRepository;
import lombok.NonNull;

@Component
public class InsertClassFlowItem {

	@Autowired
	private ClassRepository classRepository;

	@Autowired
	private UpdateClassFlowItem updateClassFlowItem;

	@Autowired
	private ClassMapper classMapper;

	public ClassDTO insert(@NonNull ClassDTO clazz) {

		if (Objects.nonNull(clazz.getIdentifier())) {
			return updateClassFlowItem.update(clazz);
		}

		return classMapper.toDTO(classRepository.save(classMapper.toEntity(clazz)));
	}
}
