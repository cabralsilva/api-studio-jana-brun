package br.com.api.flow.registeritem.item;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.com.api.converter.RegisterItemMapper;
import br.com.api.dto.RegisterItemDTO;
import br.com.api.entity.repository.RegisterItemRepository;

@Component
public class InsertRegisterItemFlowItem {

	@Autowired
	private RegisterItemRepository registerItemRepository;

	@Autowired
	private UpdateRegisterItemFlowItem updateRegisterItemFlowItem;

	@Autowired
	private RegisterItemMapper registerItemMapper;

	public RegisterItemDTO insert(@NonNull RegisterItemDTO register) {

		if (Objects.nonNull(register.getIdentifier())) {
			return updateRegisterItemFlowItem.update(register);
		}

		return registerItemMapper.toDTO(registerItemRepository.save(registerItemMapper.toEntity(register)));
	}
}
