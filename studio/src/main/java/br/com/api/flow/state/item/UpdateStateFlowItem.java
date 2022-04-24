package br.com.api.flow.state.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.StateMapper;
import br.com.api.dto.StateDTO;
import br.com.api.entity.repository.StateRepository;

@Component
public class UpdateStateFlowItem {

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private StateMapper stateMapper;

	public StateDTO update(StateDTO state) {

		return stateMapper.toDTO(stateRepository.save(stateMapper.toEntity(state)));
	}
}
