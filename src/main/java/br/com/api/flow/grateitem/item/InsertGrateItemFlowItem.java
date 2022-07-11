package br.com.api.flow.grateitem.item;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.com.api.converter.GrateItemMapper;
import br.com.api.dto.GrateItemDTO;
import br.com.api.entity.repository.GrateItemRepository;

@Component
public class InsertGrateItemFlowItem {

	@Autowired
	private GrateItemRepository grateRepository;

	@Autowired
	private UpdateGrateItemFlowItem updateGrateItemFlowItem;

	@Autowired
	private GrateItemMapper grateMapper;

	public GrateItemDTO insert(@NonNull GrateItemDTO grate) {

		if (Objects.nonNull(grate.getIdentifier())) {
			return updateGrateItemFlowItem.update(grate);
		}

		return grateMapper.toDTO(grateRepository.save(grateMapper.toEntity(grate)));
	}
}
