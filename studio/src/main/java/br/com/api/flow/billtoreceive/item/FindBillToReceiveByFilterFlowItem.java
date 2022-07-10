package br.com.api.flow.billtoreceive.item;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.BillToReceiveMapper;
import br.com.api.entity.BillToReceive;
import br.com.api.entity.repository.BillToReceiveFilter;
import br.com.api.entity.repository.BillToReceiveRepository;
import lombok.var;

@Component
public class FindBillToReceiveByFilterFlowItem {

	@Autowired
	private BillToReceiveRepository billToReceiveRepository;

	@Autowired
	private BillToReceiveMapper billToReceiveMapper;

	public BillToReceiveFilter findByFilter(BillToReceiveFilter filter) {

		Example<BillToReceive> example = Example.of(billToReceiveMapper.toEntity(filter.getExample()));
		if (Boolean.TRUE.equals(filter.getPageable())) {
			final var ret = billToReceiveRepository.findAll(example,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));
			filter.setResult(ret.stream().map(p -> billToReceiveMapper.toDTO(p)).collect(Collectors.toList()));
			filter.setTotal((int) ret.getTotalElements());
			filter.setTotalPages(ret.getTotalPages());
			return filter;
		}
		final var ret = billToReceiveRepository.findAll(example);
		filter.setResult(ret.stream().map(p -> billToReceiveMapper.toDTO(p)).collect(Collectors.toList()));
		return filter;
	}
}
