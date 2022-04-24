package br.com.api.flow.grateitem.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.GrateItemMapper;
import br.com.api.dto.GrateItemDTO;
import br.com.api.entity.repository.GrateItemRepository;

@Component
public class UpdateGrateItemFlowItem {

	@Autowired
	private GrateItemRepository grateRepository;

	@Autowired
	private GrateItemMapper grateMapper;

	public GrateItemDTO update(GrateItemDTO grate) {

		return grateMapper.toDTO(grateRepository.save(grateMapper.toEntity(grate)));
	}
}
