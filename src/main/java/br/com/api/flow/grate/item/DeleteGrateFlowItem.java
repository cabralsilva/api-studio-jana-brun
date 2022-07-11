package br.com.api.flow.grate.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.entity.repository.GrateRepository;

@Component
public class DeleteGrateFlowItem {

	@Autowired
	private GrateRepository grateRepository;

	public void delete(Integer identifier) {

		grateRepository.deleteById(identifier);
	}
}
