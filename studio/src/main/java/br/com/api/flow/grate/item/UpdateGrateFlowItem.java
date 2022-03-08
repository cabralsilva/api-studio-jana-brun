package br.com.api.flow.grate.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.GrateMapper;
import br.com.api.dto.GrateDTO;
import br.com.api.entity.repository.GrateRepository;

@Component
public class UpdateGrateFlowItem {

	@Autowired
	private GrateRepository grateRepository;

	@Autowired
	private GrateMapper grateMapper;

	public GrateDTO update(GrateDTO grate) {

		return grateMapper.toDTO(grateRepository.save(grateMapper.toEntity(grate)));
	}
}
