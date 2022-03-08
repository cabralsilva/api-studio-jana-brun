package br.com.api.flow.register.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.RegisterMapper;
import br.com.api.dto.RegisterDTO;
import br.com.api.entity.repository.RegisterRepository;

@Component
public class UpdateRegisterFlowItem {

	@Autowired
	private RegisterRepository registerRepository;

	@Autowired
	private RegisterMapper registerMapper;

	public RegisterDTO update(RegisterDTO register) {

		return registerMapper.toDTO(registerRepository.save(registerMapper.toEntity(register)));
	}
}
