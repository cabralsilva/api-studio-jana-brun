package br.com.api.flow.matriculation.item;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.com.api.converter.MatriculationMapper;
import br.com.api.dto.MatriculationDTO;
import br.com.api.entity.repository.MatriculationRepository;
import br.com.api.flow.billtoreceiveinstallment.item.CreateBillToReceiveInstallmentFlowItem;

@Component
public class InsertMatriculationFlowItem {

	@Autowired
	private MatriculationRepository matriculationRepository;

	@Autowired
	private UpdateMatriculationFlowItem updateMatriculationFlowItem;

	@Autowired
	private MatriculationMapper matriculationMapper;
	
	@Autowired
	private CreateBillToReceiveInstallmentFlowItem createBillToReceiveInstallmentFlowItem;

	public MatriculationDTO insert(@NonNull MatriculationDTO matriculationDTO) {

		if (Objects.nonNull(matriculationDTO.getIdentifier())) {
			return updateMatriculationFlowItem.update(matriculationDTO);
		}

		return matriculationMapper.toDTO(matriculationRepository.save(matriculationMapper.toEntity(matriculationDTO)));
	}
}
