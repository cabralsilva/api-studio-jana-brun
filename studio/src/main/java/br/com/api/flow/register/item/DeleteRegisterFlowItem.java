package br.com.api.flow.register.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.entity.repository.RegisterRepository;

@Component
public class DeleteRegisterFlowItem {

	@Autowired
	private RegisterRepository registerRepository;

	public void delete(Integer identifier) {

		registerRepository.deleteById(identifier);
	}
}
