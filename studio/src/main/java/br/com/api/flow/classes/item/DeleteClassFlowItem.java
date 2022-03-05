package br.com.api.flow.classes.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.entity.repository.ClassRepository;

@Component
public class DeleteClassFlowItem {

	@Autowired
	private ClassRepository classRepository;

	public void delete(Integer identifier) {

		classRepository.deleteById(identifier);
	}
}
