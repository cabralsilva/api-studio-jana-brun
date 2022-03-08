package br.com.api.flow.register.item;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.com.api.converter.RegisterMapper;
import br.com.api.dto.RegisterDTO;
import br.com.api.entity.repository.RegisterRepository;

@Component
public class InsertRegisterFlowItem {

	@Autowired
	private RegisterRepository registerRepository;

	@Autowired
	private UpdateRegisterFlowItem updateRegisterFlowItem;

	@Autowired
	private RegisterMapper registerMapper;

	public RegisterDTO insert(@NonNull RegisterDTO register) {

		if (Objects.nonNull(register.getIdentifier())) {
			return updateRegisterFlowItem.update(register);
		}

		return registerMapper.toDTO(registerRepository.save(registerMapper.toEntity(register)));
	}
}
