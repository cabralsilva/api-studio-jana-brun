package br.com.api.flow.matriculationitem.item;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.com.api.converter.MatriculationItemMapper;
import br.com.api.dto.MatriculationItemDTO;
import br.com.api.entity.repository.MatriculationItemRepository;

@Component
public class InsertMatriculationItemFlowItem {

	@Autowired
	private MatriculationItemRepository MatriculationItemRepository;

	@Autowired
	private UpdateMatriculationItemFlowItem updateMatriculationItemFlowItem;

	@Autowired
	private MatriculationItemMapper MatriculationItemMapper;

	public MatriculationItemDTO insert(@NonNull MatriculationItemDTO Matriculation) {

		if (Objects.nonNull(Matriculation.getIdentifier())) {
			return updateMatriculationItemFlowItem.update(Matriculation);
		}

		return MatriculationItemMapper.toDTO(MatriculationItemRepository.save(MatriculationItemMapper.toEntity(Matriculation)));
	}
}
