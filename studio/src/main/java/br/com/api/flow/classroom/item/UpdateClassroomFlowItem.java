package br.com.api.flow.classroom.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.ClassroomMapper;
import br.com.api.dto.ClassroomDTO;
import br.com.api.entity.repository.ClassroomRepository;

@Component
public class UpdateClassroomFlowItem {

	@Autowired
	private ClassroomRepository classroomRepository;

	@Autowired
	private ClassroomMapper classroomMapper;

	public ClassroomDTO update(ClassroomDTO clazz) {

		return classroomMapper.toDTO(classroomRepository.save(classroomMapper.toEntity(clazz)));
	}
}
