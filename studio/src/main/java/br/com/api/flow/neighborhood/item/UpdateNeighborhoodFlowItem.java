package br.com.api.flow.neighborhood.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.NeighborhoodMapper;
import br.com.api.dto.NeighborhoodDTO;
import br.com.api.entity.repository.NeighborhoodRepository;

@Component
public class UpdateNeighborhoodFlowItem {

	@Autowired
	private NeighborhoodRepository neighborhoodRepository;

	@Autowired
	private NeighborhoodMapper neighborhoodMapper;

	public NeighborhoodDTO update(NeighborhoodDTO neighborhood) {

		return neighborhoodMapper.toDTO(neighborhoodRepository.save(neighborhoodMapper.toEntity(neighborhood)));
	}
}
