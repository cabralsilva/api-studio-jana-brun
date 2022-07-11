package br.com.api.flow.matriculation.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.entity.repository.MatriculationRepository;

@Component
public class DeleteMatriculationFlowItem {

	@Autowired
	private MatriculationRepository MatriculationRepository;

	public void delete(Integer identifier) {

		MatriculationRepository.deleteById(identifier);
	}
}
