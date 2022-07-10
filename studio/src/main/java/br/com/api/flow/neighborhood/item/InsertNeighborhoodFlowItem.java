package br.com.api.flow.neighborhood.item;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.com.api.converter.NeighborhoodMapper;
import br.com.api.dto.NeighborhoodDTO;
import br.com.api.entity.repository.NeighborhoodRepository;

@Component
public class InsertNeighborhoodFlowItem {

	@Autowired
	private NeighborhoodRepository neighborhoodRepository;

	@Autowired
	private UpdateNeighborhoodFlowItem updateNeighborhoodFlowItem;

	@Autowired
	private NeighborhoodMapper neighborhoodMapper;

	public NeighborhoodDTO insert(@NonNull NeighborhoodDTO neighborhood) {

		if (Objects.nonNull(neighborhood.getIdentifier())) {
			return updateNeighborhoodFlowItem.update(neighborhood);
		}

		return neighborhoodMapper.toDTO(neighborhoodRepository.save(neighborhoodMapper.toEntity(neighborhood)));
	}
}
