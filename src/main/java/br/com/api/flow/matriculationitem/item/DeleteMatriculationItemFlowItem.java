package br.com.api.flow.matriculationitem.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.entity.repository.MatriculationRepository;

@Component
public class DeleteMatriculationItemFlowItem {

	@Autowired
	private MatriculationRepository MatriculationRepository;

	public void delete(Integer identifier) {

		MatriculationRepository.deleteById(identifier);
	}
}