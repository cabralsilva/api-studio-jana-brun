package br.com.api.flow.matriculation.item;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.MatriculationMapper;
import br.com.api.entity.Matriculation;
import br.com.api.entity.Product;
import br.com.api.entity.repository.MatriculationFilter;
import br.com.api.entity.repository.MatriculationRepository;
import br.com.api.entity.repository.MatriculationRepositoryImpl;
import lombok.var;

@Component
public class FindMatriculationByFilterFlowItem {

	@Autowired
	private MatriculationRepositoryImpl MatriculationRepositoryImpl;

	@Autowired
	private MatriculationRepository matriculationRepository;

	@Autowired
	private MatriculationMapper matriculationMapper;

	public MatriculationFilter findByFilter(MatriculationFilter filter) {

		Example<Matriculation> example = Example.of(matriculationMapper.toEntity(filter.getExample()));
		if (Boolean.TRUE.equals(filter.getPageable())) {
			final var ret = matriculationRepository.findAll(example,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));
			filter.setResult(ret.stream().map(p -> matriculationMapper.toDTO(p)).collect(Collectors.toList()));
			filter.setTotal((int) ret.getTotalElements());
			filter.setTotalPages(ret.getTotalPages());
			return filter;
		}
		final var ret = matriculationRepository.findAll(example);
		filter.setResult(ret.stream().map(p -> matriculationMapper.toDTO(p)).collect(Collectors.toList()));
		return filter;
	}
}
