package br.com.api.flow.matriculation.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.MatriculationMapper;
import br.com.api.dto.MatriculationDTO;
import br.com.api.entity.repository.MatriculationRepository;

@Component
public class UpdateMatriculationFlowItem {

	@Autowired
	private MatriculationRepository MatriculationRepository;

	@Autowired
	private MatriculationMapper MatriculationMapper;

	public MatriculationDTO update(MatriculationDTO Matriculation) {

		return MatriculationMapper.toDTO(MatriculationRepository.save(MatriculationMapper.toEntity(Matriculation)));
	}
}
