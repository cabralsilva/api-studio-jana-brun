package br.com.api.flow.matriculationitem.item;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.ClassMapper;
import br.com.api.converter.MatriculationMapper;
import br.com.api.dto.ClassDTO;
import br.com.api.dto.MatriculationDTO;
import br.com.api.entity.Matriculation;
import br.com.api.entity.repository.MatriculationRepository;

@Component
public class SearchContainingClassByFilterFlowItem {

	@Autowired
	private MatriculationRepository matriculationRepository;

	@Autowired
	private MatriculationMapper matriculationMapper;

	@Autowired
	private ClassMapper classMapper;

	public List<MatriculationDTO> search(ClassDTO clazz) {

		List<Matriculation> matriculationList = matriculationRepository.findByClassList(classMapper.toEntity(clazz));

		return matriculationList.stream().map(entity -> matriculationMapper.toDTO(entity)).collect(Collectors.toList());
	}

	public Long count(ClassDTO clazz) {

		return matriculationRepository.countByClassList(classMapper.toEntity(clazz));
	}
}
