package br.com.api.flow.grate.item;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.com.api.converter.GrateMapper;
import br.com.api.dto.GrateDTO;
import br.com.api.entity.repository.GrateRepository;

@Component
public class InsertGrateFlowItem {

	@Autowired
	private GrateRepository grateRepository;

	@Autowired
	private UpdateGrateFlowItem updateGrateFlowItem;

	@Autowired
	private GrateMapper grateMapper;

	public GrateDTO insert(@NonNull GrateDTO grate) {

		if (Objects.nonNull(grate.getIdentifier())) {
			return updateGrateFlowItem.update(grate);
		}

		return grateMapper.toDTO(grateRepository.save(grateMapper.toEntity(grate)));
	}
}
