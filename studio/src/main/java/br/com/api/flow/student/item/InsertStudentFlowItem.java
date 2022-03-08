package br.com.api.flow.student.item;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.com.api.converter.StudentMapper;
import br.com.api.dto.StudentDTO;
import br.com.api.entity.repository.StudentRepository;

@Component
public class InsertStudentFlowItem {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private UpdateStudentFlowItem updateStudentFlowItem;

	@Autowired
	private StudentMapper studentMapper;

	public StudentDTO insert(@NonNull StudentDTO student) {

		if (Objects.nonNull(student.getIdentifier())) {
			return updateStudentFlowItem.update(student);
		}

		return studentMapper.toDTO(studentRepository.save(studentMapper.toEntity(student)));
	}
}
