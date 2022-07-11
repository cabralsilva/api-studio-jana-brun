package br.com.api.flow.student.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.StudentMapper;
import br.com.api.dto.StudentDTO;
import br.com.api.entity.repository.StudentRepository;

@Component
public class UpdateStudentFlowItem {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private StudentMapper studentMapper;

	public StudentDTO update(StudentDTO student) {

		return studentMapper.toDTO(studentRepository.save(studentMapper.toEntity(student)));
	}
}
