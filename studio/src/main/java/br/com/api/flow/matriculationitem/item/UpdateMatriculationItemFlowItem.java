package br.com.api.flow.matriculationitem.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.MatriculationItemMapper;
import br.com.api.dto.MatriculationItemDTO;
import br.com.api.entity.repository.MatriculationItemRepository;

@Component
public class UpdateMatriculationItemFlowItem {

	@Autowired
	private MatriculationItemRepository MatriculationItemRepository;

	@Autowired
	private MatriculationItemMapper MatriculationItemMapper;

	public MatriculationItemDTO update(MatriculationItemDTO Matriculation) {

		return MatriculationItemMapper.toDTO(MatriculationItemRepository.save(MatriculationItemMapper.toEntity(Matriculation)));
	}
}
