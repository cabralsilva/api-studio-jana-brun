package br.com.api.flow.matriculation.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.dto.MatriculationDTO;
import br.com.api.entity.repository.MatriculationRepository;

@Component
public class UpdateStatusMatriculationFlowItem {

	@Autowired
	private MatriculationRepository MatriculationRepository;

	public void update(MatriculationDTO MatriculationDTO) {

		MatriculationRepository.updateStatusByIdentifier(MatriculationDTO.getStatus(), MatriculationDTO.getIdentifier());
	}
}
