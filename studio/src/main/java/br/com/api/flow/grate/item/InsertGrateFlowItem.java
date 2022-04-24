package br.com.api.flow.grate.item;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.com.api.converter.GrateMapper;
import br.com.api.dto.GrateDTO;
import br.com.api.dto.GrateItemDTO;
import br.com.api.entity.repository.GrateRepository;
import br.com.api.flow.grateitem.item.InsertGrateItemFlowItem;

@Component
public class InsertGrateFlowItem {

	@Autowired
	private GrateRepository grateRepository;

	@Autowired
	private UpdateGrateFlowItem updateGrateFlowItem;
	
	@Autowired
	private InsertGrateItemFlowItem grateItemFlowItem;

	@Autowired
	private GrateMapper grateMapper;

	public GrateDTO insert(@NonNull GrateDTO grateDTO) {

		if (Objects.nonNull(grateDTO.getIdentifier())) {
			return updateGrateFlowItem.update(grateDTO);
		}

		final GrateDTO grateDTOAux = grateMapper.toDTO(grateRepository.save(grateMapper.toEntity(grateDTO)));
		
		for (GrateItemDTO grateItem : grateDTO.getItemList()) {
			grateItem.setGrate(GrateDTO.builder().identifier(grateDTOAux.getIdentifier()).build());
			grateItem = grateItemFlowItem.insert(grateItem);
		}
		
		return grateDTOAux;
	}
}
