package br.com.api.flow.classroom.item;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.ClassroomMapper;
import br.com.api.dto.ClassroomDTO;
import br.com.api.entity.repository.ClassroomRepository;
import lombok.NonNull;

@Component
public class InsertClassroomFlowItem {

	@Autowired
	private ClassroomRepository classroomRepository;

	@Autowired
	private UpdateClassroomFlowItem updateClassroomFlowItem;

	@Autowired
	private ClassroomMapper classroomMapper;

	public ClassroomDTO insert(@NonNull ClassroomDTO clazz) {

		if (Objects.nonNull(clazz.getIdentifier())) {
			return updateClassroomFlowItem.update(clazz);
		}

		return classroomMapper.toDTO(classroomRepository.save(classroomMapper.toEntity(clazz)));
	}
}
