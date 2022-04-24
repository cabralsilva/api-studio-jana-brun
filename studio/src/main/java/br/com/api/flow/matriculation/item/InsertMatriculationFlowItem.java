package br.com.api.flow.matriculation.item;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.com.api.converter.MatriculationMapper;
import br.com.api.dto.MatriculationDTO;
import br.com.api.entity.repository.MatriculationRepository;

@Component
public class InsertMatriculationFlowItem {

	@Autowired
	private MatriculationRepository MatriculationRepository;

	@Autowired
	private UpdateMatriculationFlowItem updateMatriculationFlowItem;

	@Autowired
	private MatriculationMapper MatriculationMapper;

	public MatriculationDTO insert(@NonNull MatriculationDTO Matriculation) {

		if (Objects.nonNull(Matriculation.getIdentifier())) {
			return updateMatriculationFlowItem.update(Matriculation);
		}

		return MatriculationMapper.toDTO(MatriculationRepository.save(MatriculationMapper.toEntity(Matriculation)));
	}
}
