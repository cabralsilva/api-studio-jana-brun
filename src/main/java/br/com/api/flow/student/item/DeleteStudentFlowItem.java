package br.com.api.flow.student.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.entity.repository.StudentRepository;

@Component
public class DeleteStudentFlowItem {

	@Autowired
	private StudentRepository studentRepository;

	public void delete(Integer identifier) {

		studentRepository.deleteById(identifier);
	}
}
