package br.com.api.flow.state.item;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.com.api.converter.StateMapper;
import br.com.api.dto.StateDTO;
import br.com.api.entity.repository.StateRepository;

@Component
public class InsertStateFlowItem {

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private UpdateStateFlowItem updateStateFlowItem;

	@Autowired
	private StateMapper stateMapper;

	public StateDTO insert(@NonNull StateDTO state) {

		if (Objects.nonNull(state.getIdentifier())) {
			return updateStateFlowItem.update(state);
		}

		return stateMapper.toDTO(stateRepository.save(stateMapper.toEntity(state)));
	}
}
