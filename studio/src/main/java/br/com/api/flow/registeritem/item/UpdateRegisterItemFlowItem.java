package br.com.api.flow.registeritem.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.RegisterItemMapper;
import br.com.api.dto.RegisterItemDTO;
import br.com.api.entity.repository.RegisterItemRepository;

@Component
public class UpdateRegisterItemFlowItem {

	@Autowired
	private RegisterItemRepository registerItemRepository;

	@Autowired
	private RegisterItemMapper registerItemMapper;

	public RegisterItemDTO update(RegisterItemDTO register) {

		return registerItemMapper.toDTO(registerItemRepository.save(registerItemMapper.toEntity(register)));
	}
}
