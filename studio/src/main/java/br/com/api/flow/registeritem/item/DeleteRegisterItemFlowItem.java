package br.com.api.flow.registeritem.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.entity.repository.RegisterRepository;

@Component
public class DeleteRegisterItemFlowItem {

	@Autowired
	private RegisterRepository registerRepository;

	public void delete(Integer identifier) {

		registerRepository.deleteById(identifier);
	}
}
