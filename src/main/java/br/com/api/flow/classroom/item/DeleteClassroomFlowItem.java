package br.com.api.flow.classroom.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.entity.repository.ClassroomRepository;

@Component
public class DeleteClassroomFlowItem {

	@Autowired
	private ClassroomRepository classroomRepository;

	public void delete(Integer identifier) {

		classroomRepository.deleteById(identifier);
	}
}
