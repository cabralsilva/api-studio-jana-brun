package br.com.api.flow.grateitem.item;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.GrateItemMapper;
import br.com.api.entity.GrateItem;
import br.com.api.entity.repository.GrateItemFilter;
import br.com.api.entity.repository.GrateItemRepository;
import br.com.api.entity.repository.GrateItemRepositoryImpl;
import lombok.var;

@Component
public class FindGrateItemByFilterFlowItem {

	@Autowired
	private GrateItemRepositoryImpl grateItemRepositoryImpl;

	@Autowired
	private GrateItemRepository grateItemRepository;

	@Autowired
	private GrateItemMapper grateItemMapper;

	public GrateItemFilter findByFilter(GrateItemFilter filter) {

		Example<GrateItem> example = Example.of(grateItemMapper.toEntity(filter.getExample()));
		if (Boolean.TRUE.equals(filter.getPageable())) {
			final var ret = grateItemRepository.findAll(example,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));
			filter.setResult(ret.stream().map(p -> grateItemMapper.toDTO(p)).collect(Collectors.toList()));
			return filter;
		}
		final var ret = grateItemRepository.findAll(example);
		filter.setResult(ret.stream().map(p -> grateItemMapper.toDTO(p)).collect(Collectors.toList()));
		return filter;
	}
}
