package br.com.api.flow.pricetable.item;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.PriceTableMapper;
import br.com.api.entity.PriceTable;
import br.com.api.entity.repository.PriceTableFilter;
import br.com.api.entity.repository.PriceTableRepository;
import lombok.var;

@Component
public class FindPriceTableByFilterFlowItem {

	@Autowired
	private PriceTableRepository priceTableRepository;

	@Autowired
	private PriceTableMapper priceTableMapper;

	public PriceTableFilter findByFilter(PriceTableFilter filter) {

		Example<PriceTable> example = Example.of(priceTableMapper.toEntity(filter.getExample()));
		if (Boolean.TRUE.equals(filter.getPageable())) {
			final var ret = priceTableRepository.findAll(example,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));
			filter.setResult(ret.stream().map(p -> priceTableMapper.toDTO(p)).collect(Collectors.toList()));
			filter.setTotal((int) ret.getTotalElements());
			filter.setTotalPages(ret.getTotalPages());
			return filter;
		}
		final var ret = priceTableRepository.findAll(example);
		filter.setResult(ret.stream().map(p -> priceTableMapper.toDTO(p)).collect(Collectors.toList()));
		return filter;
	}
}
