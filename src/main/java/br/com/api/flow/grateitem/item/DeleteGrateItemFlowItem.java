package br.com.api.flow.grateitem.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.entity.repository.GrateItemRepository;

@Component
public class DeleteGrateItemFlowItem {

	@Autowired
	private GrateItemRepository grateRepository;

	public void delete(Integer identifier) {

		grateRepository.deleteById(identifier);
	}
}
